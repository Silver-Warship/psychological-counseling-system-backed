package org.example.psychologicalcounseling.module.user.login;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String password;
    private String verificationCode;
}
