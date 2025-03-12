package org.example.psychologicalcounseling.module.user.login;

import org.example.psychologicalcounseling.dto.LoginRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserLoginController {
    @Autowired//自动插入
    private UserLoginService userLoginService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto loginRequest) {
        if (userLoginService.authenticate(loginRequest.getEmail(), loginRequest.getPassword())) {
            return "login success";
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }

}
