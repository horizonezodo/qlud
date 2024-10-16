package com.example.qlud.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginResponse {
    String userName;
    String userRole;
    String userEmail;
    String phoneNumber;
    String accessToken;
    String refreshToken;
}
