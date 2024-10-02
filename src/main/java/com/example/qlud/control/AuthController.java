package com.example.qlud.control;

import com.example.qlud.Jwt.JwtUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class AuthController {

    @Autowired
    JwtUntil until;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private String getUsernameFromToken(String redisToken){
        for(String key: redisTemplate.keys("credentials:*")){
            String redisStoredToken = (String) redisTemplate.opsForHash().get(key,"token");
            if (redisToken.equals(redisStoredToken)){
                return (String) redisTemplate.opsForHash().get(key, "username");
            }
        }
        return null;
    }

    @GetMapping("/")
    public String ShowLoginPage(Model model, @CookieValue(value = "rememberMeToken", required = false)String redisToken){
        if (redisToken != null){
            String username = getUsernameFromToken(redisToken);
            if(username != null){
                Map<Object, Object> userCredentials = redisTemplate.opsForHash().entries("credentials:" + username);
                if(!userCredentials.isEmpty()){
                    model.addAttribute("username", userCredentials.get("username"));
                    model.addAttribute("password", userCredentials.get("password"));
                    model.addAttribute("rememberMe", true);
                }
            }
        }
        return "pages-login-website";
    }

    @GetMapping("/register")
    public String ShowRegisterPage(Model model){
        return "register";
    }

}
