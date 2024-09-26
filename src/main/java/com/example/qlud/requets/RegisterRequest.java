package com.example.qlud.requets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    String userName;
    String userEmail;
    String password;
    String phoneNumber;
    String userRole;
}
