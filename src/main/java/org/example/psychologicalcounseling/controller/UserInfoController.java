package org.example.psychologicalcounseling.controller;


import org.example.psychologicalcounseling.dto.UserWithSessionsDto;
import org.example.psychologicalcounseling.model.User;
import org.example.psychologicalcounseling.module.safety.JwtUtilTokenBuilder;
import org.example.psychologicalcounseling.module.user.info.UserInfoService;
import org.example.psychologicalcounseling.module.user.info.EditRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserInfoController {
    @Autowired
    private JwtUtilTokenBuilder jwtUtilTokenBuilder;
    @Autowired//自动插入
    private UserInfoService userInfoService;


    //通过token获取当前登录的用户信息
    @GetMapping("/tokenVerify")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String token) {
        if (jwtUtilTokenBuilder.validateToken(token)) {
            String email = jwtUtilTokenBuilder.getEmailFromToken(token);
            Long uid = userInfoService.getUidByEmail(email);
            String nickname = userInfoService.getNicknameByEmail(email);
            User.Gender gender = userInfoService.getGenderByEmail(email);
            //return "Hello, " + email + "!";

            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("uid", uid);
            userInfo.put("email", email);
            userInfo.put("nickname", nickname);
            userInfo.put("gender", gender);

            return ResponseEntity.ok(userInfo);
        } else {
//            Map<String, String> userInfo = new HashMap<>();
//            userInfo.put("uid", "invalid token");
            return (ResponseEntity<?>) ResponseEntity.badRequest();
        }
    }

    //获取所有用户的信息
    @GetMapping("/test/getAll")
    public ResponseEntity<?> getUserList() {
        // 查询所有用户
        List<UserWithSessionsDto> userList = userInfoService.getAllUsers();

        // 创建一个List来存储用户信息，包括sessions
        List<Map<String, Object>> userInfoList = new ArrayList<>();
        for (UserWithSessionsDto user : userList) {
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("uid", user.getUser().getUid()); // 获取用户ID
            userInfo.put("email", user.getUser().getEmail());//获取用户email
            userInfo.put("gender", user.getUser().getGender());//获取用户性别
            userInfo.put("nickname", user.getUser().getNickname()); // 获取用户昵称
            userInfo.put("sessions", user.getSessions()); // 获取sessions列表
            userInfoList.add(userInfo);
        }

        // 返回JSON格式的用户信息
        return ResponseEntity.ok(userInfoList);
    }

    @PostMapping("/user/editprofile")
    public ResponseEntity<?> editUserProfile(@RequestBody EditRequestDto editRequest) {
        userInfoService.editUserProfile(editRequest);
        return ResponseEntity.ok().build();
    }

}
