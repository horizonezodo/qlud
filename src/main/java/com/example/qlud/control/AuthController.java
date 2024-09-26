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
import com.example.qlud.response.RefreshTokenResponse;
import com.example.qlud.service.RefreshTokenService;
import com.example.qlud.service.UserDetailImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/auth")
public class AuthController {

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


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailImpl userDetail = (UserDetailImpl) authentication.getPrincipal();
        String jwt = "";
        if(request.getUsername().contains("@")){
            jwt = until.generateJwtTokenForLoginUsingEmail(userDetail);
        }else{
            jwt = until.generateJwtTokenForLoginUsingPhone(userDetail);
        }
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetail.getId());
        LoginResponse response = new LoginResponse();
        response.setUserName(userDetail.getUsername());
        response.setPhoneNumber(userDetail.getPhoneNumber());
        response.setUserEmail(userDetail.getUserEmail());
        response.setUserRole(userDetail.getUserRole());
        response.setAccessToken(jwt);
        response.setRefreshToken(refreshToken.getToken());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewAccount(@RequestBody RegisterRequest request){
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
    public ResponseEntity<?> logout(){
        UserDetailImpl userDetail = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetail.getId();
        refreshTokenService.deleteByUserId(userId);
        MessageResponse mess = new MessageResponse("Logout Success");
        return new ResponseEntity<>(mess, HttpStatus.OK);
    }

    @PostMapping("check-token")
    public ResponseEntity<?> checkToken(@RequestBody CheckTokenRequest request){
        if(until.verifyToken(request.getToken())){
            return new ResponseEntity<>(new MessageResponse("Token is valid"),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new MessageResponse("Token is not Valid"), HttpStatus.BAD_REQUEST);
        }
    }
}
