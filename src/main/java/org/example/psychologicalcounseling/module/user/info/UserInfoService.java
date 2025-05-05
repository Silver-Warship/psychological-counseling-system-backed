package org.example.psychologicalcounseling.module.user.info;

import org.example.psychologicalcounseling.dto.UserWithSessionsDto;
import org.example.psychologicalcounseling.model.*;
import org.example.psychologicalcounseling.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserInfoService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final AccountRepository accountRepository;
    private final CounsellorRepository counsellorRepository;
    private final SupervisorRepository supervisorRepository;
    private final AdminRepository adminRepository;

    public UserInfoService(UserRepository userRepository, SessionRepository sessionRepository,
                           AccountRepository accountRepository, CounsellorRepository counsellorRepository,
                           SupervisorRepository supervisorRepository, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.accountRepository = accountRepository;
        this.counsellorRepository = counsellorRepository;
        this.supervisorRepository = supervisorRepository;
        this.adminRepository = adminRepository;
    }

    /**
     * 获取所有用户信息
     * @return List<UserWithSessionsDto> 用户信息列表
     */
    public List<UserWithSessionsDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserWithSessionsDto> userWithSessionsList = new ArrayList<>();

        for (User user : users) {
            Long uid = user.getUid();
            List<Session> sessions = sessionRepository.findRunningSessionByUserID(uid);
            userWithSessionsList.add(new UserWithSessionsDto(user, sessions));
        }

        return userWithSessionsList;
    }

    /**
     * 根据用户email获取用户信息
     * @param email 用户email
     * @return User 用户信息
     */
    public User getUserByEmail(String email) {
        // 从数据库中获取用户信息，并检查用户是否存在
        User user = userRepository.findByEmail(email);
        if (user == null) {
            // 用户不存在，返回-1或其他错误处理
            return null;
        }
        return user;
    }

    /**
     * 根据用户email获取咨询师信息
     * @param email 咨询师email
     * @return User 咨询师信息
     */
    public Counsellor getCounsellorByEmail(String email) {
        // 从数据库中获取用户信息，并检查用户是否存在
        Counsellor counsellor = counsellorRepository.findByEmail(email);
        if (counsellor == null) {
            // 用户不存在，返回-1或其他错误处理
            return null;
        }
        return counsellor;
    }

    /**
     * 根据用户email获取监督员信息
     * @param email 监督员email
     * @return User 监督员信息
     */
    public Supervisor getSupervisorByEmail(String email) {
        // 从数据库中获取用户信息，并检查用户是否存在
        Supervisor supervisor = supervisorRepository.findByEmail(email);
        if (supervisor == null) {
            // 用户不存在，
            return null;
        }
        return supervisor;
    }

    /**
     * 根据用户email获取管理员信息
     * @param email 管理员email
     * @return User 管理员信息
     */
    public Admin getAdminByEmail(String email) {
        // 从数据库中获取用户信息，并检查用户是否存在
        Admin admin = adminRepository.findByEmail(email);
        if (admin == null) {
            // 用户不存在，
            return null;
        }
        return admin;
    }

    /**
     * 编辑用户信息
     * @param editRequestDto 用户编辑请求数据传输对象
     */
    public void editUserProfile(EditRequestDto editRequestDto) {
        try {
            Long uid = editRequestDto.getUid();
            // 根据uid获取用户实体
            User user = userRepository.findById(uid).orElseThrow(() -> new RuntimeException("User not found"));
            Account account = accountRepository.findById(uid).orElseThrow(() -> new RuntimeException("Account not found"));

            // 更新不为空的字段
            if (editRequestDto.getGender() != null) {
                user.setGender(editRequestDto.getGender());
            }
            if (editRequestDto.getNickname() != null && editRequestDto.getNickname() != "") {
                user.setNickname(editRequestDto.getNickname());
            }
            if (editRequestDto.getPassword() != null && editRequestDto.getPassword() != "") {
                account.setPassword(editRequestDto.getPassword());
            }

            // 保存更新后的用户信息
            userRepository.save(user);
            accountRepository.save(account);

        } catch (Exception e) {
        }
    }

}
