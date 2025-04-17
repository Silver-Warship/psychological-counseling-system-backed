package org.example.psychologicalcounseling.module.user.info;

import org.example.psychologicalcounseling.dto.UserWithSessionsDto;
import org.example.psychologicalcounseling.model.Account;
import org.example.psychologicalcounseling.model.Admin;
import org.example.psychologicalcounseling.model.Session;
import org.example.psychologicalcounseling.model.User;
import org.example.psychologicalcounseling.repository.AccountRepository;
import org.example.psychologicalcounseling.repository.SessionRepository;
import org.example.psychologicalcounseling.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserInfoService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    AccountRepository accountRepository;

    public List<UserWithSessionsDto> getAllUsers() {
        // 从数据库中获取所有用户信息
//        List<User> users = userRepository.findAll();
//        return users;
        List<User> users = userRepository.findAll();
        List<UserWithSessionsDto> userWithSessionsList = new ArrayList<>();

        for (User user : users) {
            Long uid = user.getUid();
            List<Session> sessions = sessionRepository.findRunningSessionByUserID(uid);
            userWithSessionsList.add(new UserWithSessionsDto(user, sessions));
        }

        return userWithSessionsList;
    }

    public Long getUidByEmail(String email) {
        // 从数据库中获取用户信息，并检查用户是否存在
        User user = userRepository.findByEmail(email);
        if (user == null) {
            // 用户不存在，返回-1或其他错误处理
            return -1L;
        }
        return user.getUid();
    }

    public String getNicknameByEmail(String email) {
        // 从数据库中获取用户信息，并检查用户是否存在
        User user = userRepository.findByEmail(email);
        if (user == null) {
            // 用户不存在，返回-1或其他错误处理
            return null;
        }
        return user.getNickname();
    }

    public User.Gender getGenderByEmail(String email) {
        // 从数据库中获取用户信息，并检查用户是否存在
        User user = userRepository.findByEmail(email);
        if (user == null) {
            // 用户不存在，返回-1或其他错误处理
            return null;
        }
        return user.getGender();
    }

    public boolean editUserProfile (EditRequestDto editRequestDto){
        try{
            Long uid = editRequestDto.getUid();
            // 根据uid获取用户实体
            User user = userRepository.findById(uid).orElseThrow(() -> new RuntimeException("User not found"));
            Account account = accountRepository.findById(uid).orElseThrow(() -> new RuntimeException("Account not found"));

            // 更新不为空的字段
            if (editRequestDto.getGender() != null ) {
                user.setGender(editRequestDto.getGender());
            }
            if (editRequestDto.getNickname() != null && editRequestDto.getNickname()!="") {
                user.setNickname(editRequestDto.getNickname());
            }
            if (editRequestDto.getPassword() != null && editRequestDto.getPassword()!="") {
                account.setPassword(editRequestDto.getPassword());
            }

            // 保存更新后的用户信息
            userRepository.save(user);
            accountRepository.save(account);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
