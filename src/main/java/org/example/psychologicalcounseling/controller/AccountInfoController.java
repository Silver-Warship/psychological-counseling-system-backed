package org.example.psychologicalcounseling.controller;

import org.example.psychologicalcounseling.dto.UserWithSessionsDto;
import org.example.psychologicalcounseling.model.Admin;
import org.example.psychologicalcounseling.model.Counsellor;
import org.example.psychologicalcounseling.model.Supervisor;
import org.example.psychologicalcounseling.model.User;
import org.example.psychologicalcounseling.module.safety.JwtUtilTokenBuilder;
import org.example.psychologicalcounseling.module.info.AccountInfoService;
import org.example.psychologicalcounseling.module.info.EditRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AccountInfoController {
    private final JwtUtilTokenBuilder jwtUtilTokenBuilder;
    private final AccountInfoService accountInfoService;

    public AccountInfoController(JwtUtilTokenBuilder jwtUtilTokenBuilder, AccountInfoService accountInfoService) {
        this.jwtUtilTokenBuilder = jwtUtilTokenBuilder;
        this.accountInfoService = accountInfoService;
    }

    /**
     * 通过token获取当前登录的用户信息
     * @param token 用户的token
     * @param role 用户的角色
     * @return 用户信息
     */
    @GetMapping("/tokenVerify")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String token, @RequestParam String role) {
        String email = jwtUtilTokenBuilder.getEmailFromToken(token);

        Long uid = null;
        String nickname = "";
        String gender = "";
        //return "Hello, " + email + "!";

        switch (role){
            case "user":
                User user = accountInfoService.getUserByEmail(email);
                uid = user.getUid();
                nickname = user.getNickname();
                gender = user.getGender().toString();
                break;
            case "counsellor":
                Counsellor counsellor = accountInfoService.getCounsellorByEmail(email);
                uid = counsellor.getCounsellorID();
                nickname = counsellor.getNickname();
                gender = counsellor.getGender().toString();
                break;
            case "supervisor":
                Supervisor supervisor = accountInfoService.getSupervisorByEmail(email);
                uid = supervisor.getSupervisorID();
                nickname = supervisor.getNickname();
                gender = supervisor.getGender().toString();
                break;
            case "admin":
                Admin admin = accountInfoService.getAdminByEmail(email);
                uid = admin.getAdminID();
                nickname = admin.getNickname();
                gender = admin.getGender().toString();
                break;
            default:
                return ResponseEntity.badRequest().body("Invalid role");

        }

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("uid", uid);
        userInfo.put("email", email);
        userInfo.put("nickname", nickname);
        userInfo.put("gender", gender);
        return ResponseEntity.ok(userInfo);
    }

    /**
     * 获取所有用户信息，包括sessions
     * @return List<Map<String, Object>> 用户信息列表
     */
    @GetMapping("/test/getAll")
    public ResponseEntity<?> getUserList() {
        // 查询所有用户
        List<UserWithSessionsDto> userList = accountInfoService.getAllUsers();

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

    /**
     * 编辑用户信息
     * @param editRequest  用户信息
     * @return             编辑结果
     */
    @PostMapping("/editprofile")
    public ResponseEntity<?> editUserProfile(@RequestBody EditRequestDto editRequest, @RequestParam String role) {
        boolean result = false;
        if(role.equals("user")){
            result = accountInfoService.editUserProfile(editRequest);
        }else if(role.equals("counsellor")){
            result = accountInfoService.editCounsellorProfile(editRequest);
        }else if(role.equals("supervisor")){
            result = accountInfoService.editSupervisorProfile(editRequest);
        }

        if(result){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 通过email搜索用户信息
     * @param email 用户的email
     * @return     用户信息
     */
    @GetMapping("/user/searchBy")
    public ResponseEntity<?> searchUserInfo(@RequestParam String email) {
        return ResponseEntity.ok().build();
    }
}
