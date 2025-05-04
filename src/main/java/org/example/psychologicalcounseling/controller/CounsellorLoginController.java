package org.example.psychologicalcounseling.controller;

import org.example.psychologicalcounseling.module.safety.OriginalPasswordBuilder;
import org.example.psychologicalcounseling.module.user.login.CounsellorLoginService;
import org.example.psychologicalcounseling.module.user.login.LoginRequestDto;
import org.example.psychologicalcounseling.dto.UserDto;
import org.example.psychologicalcounseling.module.safety.JwtUtilTokenBuilder;
import org.example.psychologicalcounseling.module.safety.VerificationCodeBuilder;
import org.example.psychologicalcounseling.module.user.login.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CounsellorLoginController {
    @Autowired//自动插入
    private CounsellorLoginService counsellorLoginService;
    @Autowired
    private JwtUtilTokenBuilder jwtUtilTokenBuilder;

    @PostMapping("/api/counsellor/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {
        LoginResponse response = new LoginResponse();
        //验证email和password是否正确
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        String verificationCode = loginRequest.getVerificationCode();
        //业务情况
        //登录：有email和password，email在数据库内，没有verification
        //注册：有email和password，email不在数据库内，有verification
        //验证注册：有email和password，email不在数据库内，没有verification
        if(password!=null && !password.isEmpty()){
            if(counsellorLoginService.checkEmail(email)){
                //登录验证
                if (counsellorLoginService.checkPassword(email, password)) {
                    //给正确的用户生成token
                    //登录成功
                    String token = jwtUtilTokenBuilder.generateToken(email);
                    response.setToken(token);
                    response.setCode(600);
                    response.setCodeMsg("Login successfully.");
                } else {
                    response.setCode(601);
                    response.setCodeMsg("The email and password are fail to pair.");
                }
            }else{
                response.setCode(601);
                response.setCodeMsg("The email is not registered.");
            }
        }else{
            //注册或者验证注册码
            if(verificationCode==null || verificationCode.isEmpty()){
                //发送验证码
                if(counsellorLoginService.SendVerificationCode(email, VerificationCodeBuilder.generateVerificationCode())){
                    response.setCode(600);
                    response.setCodeMsg("Verification code is sent to your email.");
                }else{
                    //5分钟内重复发,提示系统繁忙
                    response.setCode(602);
                    response.setCodeMsg("The system is in busy, please wait for several min.");
                }
            }else{
                //核对验证码
                if(counsellorLoginService.checkVerificationCode(email, verificationCode)){
                    if(!counsellorLoginService.checkEmail(email)){
                        UserDto userDto = new UserDto();
                        userDto.setEmail(email);
                        userDto.setPassword(OriginalPasswordBuilder.generatePassword());
                        userDto.setNickname("User"+email);
                        counsellorLoginService.addUser(userDto);
                        //response.setCode(200);
                        //response.setCodeMsg("Code is verified successfully.");
                    }
                    String token = jwtUtilTokenBuilder.generateToken(email);
                    response.setToken(token);
                    response.setCode(600);
                    response.setCodeMsg("Login successfully.");
                }else{
                    response.setCode(601);
                    response.setCodeMsg("The email and verification code are fail to pair.");
                }
            }
        }
        return response.buildResponse();
    }

}
