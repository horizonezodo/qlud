package com.example.qlud.control;

import com.example.qlud.Jwt.JwtUntil;
import com.example.qlud.exception.TokenRefreshException;
import com.example.qlud.model.CustomLog;
import com.example.qlud.model.RefreshToken;
import com.example.qlud.model.User;
import com.example.qlud.repo.UserRepo;
import com.example.qlud.requets.CheckTokenRequest;
import com.example.qlud.requets.LoginRequest;
import com.example.qlud.requets.RefreshTokenRequest;
import com.example.qlud.requets.RegisterRequest;
import com.example.qlud.response.LoginResponse;
import com.example.qlud.response.MessageResponse;
import com.example.qlud.response.RecaptchaResponse;
import com.example.qlud.response.RefreshTokenResponse;
import com.example.qlud.service.ConvertToString;
import com.example.qlud.service.RefreshTokenService;
import com.example.qlud.service.RequestLogService;
import com.example.qlud.service.UserDetailImpl;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthRestController {

    @Autowired
    CustomLog logs;

    private static final Logger logger = LoggerFactory.getLogger(CustomLog.class);

    @Autowired
    UserRepo userRepo;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    JwtUntil until;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    RequestLogService service;

    @Autowired
    AuthenticationManager manager;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    ConvertToString convert;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse cookieResponse){
        Instant startTime = Instant.now();
        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailImpl userDetail = (UserDetailImpl) authentication.getPrincipal();
        String jwt = "";
        if(request.getUsername().contains("@")){
            jwt = until.generateJwtTokenForLoginUsingEmail(userDetail);
        }else{
            jwt = until.generateJwtTokenForLoginUsingPhone(userDetail);
        }
        String redisToken = until.generateTokenFromUsername(request.getUsername());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetail.getId());
        LoginResponse response = new LoginResponse();
        response.setUserName(userDetail.getUsername());
        response.setPhoneNumber(userDetail.getPhoneNumber());
        response.setUserEmail(userDetail.getUserEmail());
        response.setUserRole(userDetail.getUserRole());
        response.setAccessToken(jwt);
        response.setRefreshToken(refreshToken.getToken());
        if(request.isRememberMe()){
            saveUserCredentialsInRedis(request.getUsername(), request.getPassword());
            Cookie cookie = new Cookie("rememberMeToken", redisToken);
            cookie.setMaxAge(7 * 24 * 60 * 60);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookieResponse.addCookie(cookie);
        }
        logger.info(logs.info("Login account: " + request.getUsername(),convert.convertToJson(request),"/auth/login",convert.convertToJson(response),"POST",startTime, 200 ));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/oauth-2-success")
    public ResponseEntity<?> oauth2LoginSuccess(@RequestBody Map<String, String> requestBody) {
        Instant startTime = Instant.now();
        String token = requestBody.get("token");
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(clientId))
                .build();
        try{
            GoogleIdToken idToken = verifier.verify(token);
            if(idToken != null){
                GoogleIdToken.Payload payload = idToken.getPayload();
                String email = payload.getEmail();
                boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
                String name = (String) payload.get("name");
                String pictureUrl = (String) payload.get("picture");
                RefreshToken refreshToken;
                if(!userRepo.existsByUserEmail(email)){
                    User user = new User();
                    user.setUserEmail(email);
                    user.setUserName(name);
                    user.setUserRole("user");
                    userRepo.save(user);
                    refreshToken = refreshTokenService.createRefreshToken(user.getId());
                }else{
                    User user = userRepo.findUserByUserEmail(email).get();
                    refreshToken = refreshTokenService.createRefreshToken(user.getId());
                }
                String jwt = until.generateTokenFromEmail(email);
                LoginResponse response = new LoginResponse();
                response.setUserName(name);
                response.setAccessToken(jwt);
                response.setRefreshToken(refreshToken.getToken());
                logger.info(logs.info("Login google success:",convert.convertMapToJson(requestBody),"/auth/oauth-2-success",convert.convertToJson(response),"POST",startTime, 200 ));
                return new ResponseEntity<>(response, HttpStatus.OK);

            }else{
                MessageResponse mess = new MessageResponse("Invalid Google Token");
                logger.info(logs.error("Login google failure: Invalid Google Token",convert.convertMapToJson(requestBody),"/auth/oauth-2-success",convert.convertToJson(mess),"POST",startTime, 401 ));
                return new ResponseEntity<>(mess, HttpStatus.UNAUTHORIZED);
            }
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            MessageResponse mess = new MessageResponse("Can not verifying token");
            logger.info(logs.error("Login google failure: an not verifying token",convert.convertMapToJson(requestBody),"/auth/oauth-2-success",convert.convertToJson(mess),"POST",startTime, 400 ));
            return new ResponseEntity<>(mess, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewAccount(@RequestBody RegisterRequest request){
        Instant startTime = Instant.now();
        String recaptchaToken = request.getRecaptchaToken();
        String recaptchaSecret = "6LfWglUqAAAAAFUUhiKNubZtN4MqluDN62b9ixeK";

        String gooleUrl = "https://www.google.com/recaptcha/api/siteverify";
        RestTemplate restTemplate = new RestTemplate();
        String requestUrl = gooleUrl + "?secret=" + recaptchaSecret + "&response=" + recaptchaToken;
        RecaptchaResponse recaptchaResponse = restTemplate.postForObject(requestUrl, null, RecaptchaResponse.class);
        if (!recaptchaResponse.isSuccess()) {
            MessageResponse mess = new MessageResponse("Invalid reCAPTCHA");
            logger.error(logs.error("Register failure : Invalid reCAPTCHA",convert.convertToJson(request),"/auth/register",convert.convertToJson(mess),"POST",startTime, 400));
            return new ResponseEntity<>(mess, HttpStatus.BAD_REQUEST);
        }
        if(userRepo.existsByUserEmail(request.getUserEmail())){
            MessageResponse mess = new MessageResponse("Email has been registered");
            logger.error(logs.error("Register failure :Email has been registered " + request.getUserEmail(),convert.convertToJson(request),"/auth/register",convert.convertToJson(mess),"POST",startTime, 400));
            return new ResponseEntity<>(mess, HttpStatus.BAD_REQUEST);
        }
        if(userRepo.existsByPhoneNumber(request.getPhoneNumber())){
            MessageResponse mess = new MessageResponse("Phone number has been registered");
            logger.info(logs.error("Register account failure:Phone number has been registered " + request.getPhoneNumber(),convert.convertToJson(request),"/auth/register",convert.convertToJson(mess),"POST",startTime, 400));
            return new ResponseEntity<>(mess, HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUserName(request.getUserName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setUserRole(request.getUserRole());
        user.setUserEmail(request.getUserEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        userRepo.save(user);
        MessageResponse mess = new MessageResponse("Register Success");
        logger.info(logs.info("Register account : " + request.getUserEmail(),convert.convertToJson(request),"/auth/register",convert.convertToJson(mess),"POST",startTime, 201));
        return new ResponseEntity<>(mess,HttpStatus.CREATED);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshTOken(@RequestBody RefreshTokenRequest request){
        Instant startTime = Instant.now();
        return refreshTokenService.findByToken(request.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String newAccessToken = "";
                    if(user.getUserEmail() != null && !user.getUserEmail().isEmpty()){
                        newAccessToken = until.generateTokenFromEmail(user.getUserEmail());
                    }else if(user.getPhoneNumber() != null && !user.getPhoneNumber().isEmpty()){
                        newAccessToken = until.generateTokenFromPhone(user.getPhoneNumber());
                    }
                    RefreshToken newRefreshToken = refreshTokenService.updateRefreshToken(request.getRefreshToken());
                    RefreshTokenResponse response = new RefreshTokenResponse(newAccessToken, newRefreshToken.getToken());
                    logger.info(logs.info("Refresh token success:",convert.convertToJson(request),"/auth/refresh-token",convert.convertToJson(response),"POST",startTime, 200 ));
                    return ResponseEntity.ok(response);
                })
                .orElseThrow(() -> new TokenRefreshException(request.getRefreshToken(), "Please Login"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(value = "rememberMeToken", required = false)String redisToken, HttpServletResponse response){
        Instant startTime = Instant.now();
        UserDetailImpl userDetail = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetail.getId();
        refreshTokenService.deleteByUserId(userId);
        if(redisToken != null){
            String username = getUsernameFromToken(redisToken);
            if(userDetail!= null){
                redisTemplate.delete("credentials:"+username);
            }
        }
        Cookie cookie = new Cookie("rememberMeToken", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        MessageResponse mess = new MessageResponse("Logout Success");
        logger.info(logs.info("Logout success","", "/auth/logout",convert.convertToJson(mess),"POST",startTime, 200 ));
        return new ResponseEntity<>(mess, HttpStatus.OK);
    }

    private String getUsernameFromToken(String redisToken){
        for(String key: redisTemplate.keys("credentials:*")){
            String redisStoredToken = (String) redisTemplate.opsForHash().get(key,"token");
            if (redisToken.equals(redisStoredToken)){
                return (String) redisTemplate.opsForHash().get(key, "username");
            }
        }
        return null;
    }

    @PostMapping("/check-token")
    public ResponseEntity<?> checkToken(@RequestBody CheckTokenRequest request){
        if(until.verifyToken(request.getToken())){
            return new ResponseEntity<>(new MessageResponse("Token is valid"),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new MessageResponse("Token is not Valid"), HttpStatus.BAD_REQUEST);
        }
    }

    private void saveUserCredentialsInRedis(String username, String pass){
        String encodePass = encodePass(pass);
        Map<String, String> userCredentials = new HashMap<>();
        userCredentials.put("username", username);
        userCredentials.put("password", encodePass);
        redisTemplate.opsForHash().putAll("credentials: " + username, userCredentials);
    }


    private String encodePass(String pass){
        return passwordEncoder.encode(pass);
    }

    private boolean matchPassword(String rawPass, String encodePass){
        return passwordEncoder.matches(rawPass, encodePass);
    }
}
