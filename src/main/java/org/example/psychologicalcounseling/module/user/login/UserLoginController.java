package org.example.psychologicalcounseling.module.user.login;

import org.example.psychologicalcounseling.dto.LoginRequestDto;
import org.example.psychologicalcounseling.module.safety.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserLoginController {
    @Autowired//自动插入
    private UserLoginService userLoginService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {
        //验证email和password是否正确
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        LoginResponse response = new LoginResponse();
        if (userLoginService.authenticate(email, password)) {
            //给正确的用户生成token
            String token = jwtUtil.generateToken(email);
            response.setToken(token);
        } else {
            response.setCode(401);
            response.setCodeMsg("Invalid username or password");
        }
        return response.buildResponse();
    }

    @GetMapping("/tokenValid")
    public String getUserInfo(@RequestHeader("Authorization") String token) {
        if (jwtUtil.validateToken(token)) {
            String email = jwtUtil.getEmailFromToken(token);
            return "Hello, " + email + "!";
        } else {
            return "Invalid token";
        }
    }

}
