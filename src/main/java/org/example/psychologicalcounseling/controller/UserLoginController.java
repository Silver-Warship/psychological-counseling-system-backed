package org.example.psychologicalcounseling.controller;

import org.example.psychologicalcounseling.model.User;
import org.example.psychologicalcounseling.module.safety.OriginalPasswordBuilder;
import org.example.psychologicalcounseling.module.user.login.LoginRequestDto;
import org.example.psychologicalcounseling.dto.UserDto;
import org.example.psychologicalcounseling.module.safety.JwtUtilTokenBuilder;
import org.example.psychologicalcounseling.module.safety.VerificationCodeBuilder;
import org.example.psychologicalcounseling.module.user.login.LoginResponse;
import org.example.psychologicalcounseling.module.user.login.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("/api/users")
public class UserLoginController {
    @Autowired//自动插入
    private UserLoginService userLoginService;
    @Autowired
    private JwtUtilTokenBuilder jwtUtilTokenBuilder;

    @PostMapping("/api/users/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {

        LoginResponse response = new LoginResponse();
        //验证email和password是否正确
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        String verificationCode = loginRequest.getVerificationCode();
        //业务情况：
        //登录：有email和password，email在数据库内，没有verification
        //注册：有email和password，email不在数据库内，有verification
        //验证注册：有email和password，email不在数据库内，没有verification

        //if(password！=null or ""){
        //  if(账户存在){验证密码}
        //  else{提示账户未注册}
        //}
        //}else{
        //   if(验证码为空){
        //     if(5分钟内){
        //       发送验证码
        //     }else{提示系统繁忙}
        //   }if{
        //     if(验证码正确){
        //       if(!userLoginService.checkEmail(email)){数据库添加初始账号和17位密码}
        //       发送token
        //     }else{
        //       发送验证码未成功匹配
        //     }
        //   }
        //}

        if(password!=null && !password.isEmpty()){
            if(userLoginService.checkEmail(email)){
                //登录验证
                if (userLoginService.checkPassword(email, password)) {
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
                if(userLoginService.SendVerificationCode(email, VerificationCodeBuilder.generateVerificationCode())){
                    response.setCode(600);
                    response.setCodeMsg("Verification code is sent to your email.");
                }else{
                    //5分钟内重复发,提示系统繁忙
                    response.setCode(602);
                    response.setCodeMsg("The system is in busy, please wait for several min.");
                }
            }else{
                //核对验证码
                if(userLoginService.checkVerificationCode(email, verificationCode)){
                    if(!userLoginService.checkEmail(email)){
                        UserDto userDto = new UserDto();
                        userDto.setEmail(email);
                        userDto.setPassword(OriginalPasswordBuilder.generatePassword());
                        userDto.setNickname("User"+email);
                        userLoginService.addUser(userDto);
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


    @GetMapping("/test/tokenVerify")
    public String getUserInfo(@RequestHeader("Authorization") String token) {
        if (jwtUtilTokenBuilder.validateToken(token)) {
            String email = jwtUtilTokenBuilder.getEmailFromToken(token);
            return "Hello, " + email + "!";
        } else {
            return "Invalid token";
        }
    }


    @GetMapping("/api/test/getAll")
    public ResponseEntity<?> getUserList() {
        // 查询所有用户
        List<User> userList = userLoginService.getAllUsers();

        // 创建一个Map来存储用户的ID和昵称
        List<Map<String, String>> userInfoList = new ArrayList<>();
        for (User user : userList) {
            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("uid", String.valueOf(user.getUid()));
            userInfo.put("nickname", user.getNickname());
            userInfoList.add(userInfo);
        }

        // 返回JSON格式的用户信息
        return ResponseEntity.ok(userInfoList);
    }

}
