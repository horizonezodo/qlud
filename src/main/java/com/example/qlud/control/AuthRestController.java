package com.example.qlud.control;

import com.example.qlud.Jwt.JwtUntil;
import com.example.qlud.exception.TokenRefreshException;
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
import com.example.qlud.service.RefreshTokenService;
import com.example.qlud.service.UserDetailImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/auth")
public class AuthRestController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    JwtUntil until;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthenticationManager manager;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse cookieResponse){
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

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewAccount(@RequestBody RegisterRequest request){
        String recaptchaToken = request.getRecaptchaToken();
        String recaptchaSecret = "6LfWglUqAAAAAFUUhiKNubZtN4MqluDN62b9ixeK";

        String gooleUrl = "https://www.google.com/recaptcha/api/siteverify";
        RestTemplate restTemplate = new RestTemplate();
        String requestUrl = gooleUrl + "?secret=" + recaptchaSecret + "&response=" + recaptchaToken;
        RecaptchaResponse recaptchaResponse = restTemplate.postForObject(requestUrl, null, RecaptchaResponse.class);
        if (!recaptchaResponse.isSuccess()) {
            MessageResponse mess = new MessageResponse("Invalid reCAPTCHA");
            return new ResponseEntity<>(mess, HttpStatus.BAD_REQUEST);
        }
        if(userRepo.existsByUserEmail(request.getUserEmail())){
            MessageResponse mess = new MessageResponse("Email has been registered");
            return new ResponseEntity<>(mess, HttpStatus.BAD_REQUEST);
        }
        if(userRepo.existsByPhoneNumber(request.getPhoneNumber())){
            MessageResponse mess = new MessageResponse("Phone number has been registered");
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
        return new ResponseEntity<>(mess,HttpStatus.CREATED);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshTOken(@RequestBody RefreshTokenRequest request){
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
                    return ResponseEntity.ok(new RefreshTokenResponse(newAccessToken, newRefreshToken.getToken()));
                })
                .orElseThrow(() -> new TokenRefreshException(request.getRefreshToken(), "Please Login"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(value = "rememberMeToken", required = false)String redisToken, HttpServletResponse response){
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
