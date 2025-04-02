package org.example.psychologicalcounseling.module.user.login;

import org.example.psychologicalcounseling.dto.LoginRequestDto;
import org.example.psychologicalcounseling.dto.UserDto;
import org.example.psychologicalcounseling.module.safety.JwtUtilTokenBuilder;
import org.example.psychologicalcounseling.module.safety.VerificationCodeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserLoginController {
    @Autowired//自动插入
    private UserLoginService userLoginService;
    @Autowired
    private JwtUtilTokenBuilder jwtUtilTokenBuilder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {
        //验证email和password是否正确
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        String verificationCode = loginRequest.getVerificationCode();

        LoginResponse response = new LoginResponse();
        //业务情况：
        //登录：有email和password，email在数据库内，没有verification
        //注册：有email和password，email不在数据库内，有verification
        //验证注册：有email和password，email不在数据库内，没有verification

        if(userLoginService.checkEmail(email)){
            if (userLoginService.checkPassword(email, password)) {
                //给正确的用户生成token
                String token = jwtUtilTokenBuilder.generateToken(email);
                response.setToken(token);
            } else {
                response.setCode(401);
                response.setCodeMsg("The email and password are fail to pair.");
            }

        }else{
            if(verificationCode==null || verificationCode.isEmpty()){
                //没有验证码就发送验证码
                if(userLoginService.SendVerificationCode(email, VerificationCodeBuilder.generateVerificationCode())){
                    response.setCode(403);
                    //response.setCodeMsg("Code is sent successfully.");
                }else{
                    //如果5分钟内重复发则提示繁忙
                    response.setCode(402);
                    response.setCodeMsg("The system is in busy, please wait for 5 min.");
                }
            }else{
                if(!userLoginService.checkVerificationCode(email, verificationCode)){
                    response.setCode(401);
                    response.setCodeMsg("The email and verification code are fail to pair.");
                }else{
                    UserDto userDto = new UserDto();
                    userDto.setEmail(email);
                    userDto.setPassword(password);
                    userDto.setNickname("User"+email);
                    userLoginService.addUser(userDto);
                    //response.setCode(200);
                    //response.setCodeMsg("Code is verified successfully.");
                }
            }
        }
        return response.buildResponse();
    }

    @GetMapping("/tokenValid")
    public String getUserInfo(@RequestHeader("Authorization") String token) {
        if (jwtUtilTokenBuilder.validateToken(token)) {
            String email = jwtUtilTokenBuilder.getEmailFromToken(token);
            return "Hello, " + email + "!";
        } else {
            return "Invalid token";
        }
    }

}
