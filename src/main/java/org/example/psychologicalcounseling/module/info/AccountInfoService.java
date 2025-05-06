package org.example.psychologicalcounseling.module.info;

import org.example.psychologicalcounseling.dto.UserWithSessionsDto;
import org.example.psychologicalcounseling.model.*;
import org.example.psychologicalcounseling.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AccountInfoService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final AccountRepository accountRepository;
    private final CounsellorRepository counsellorRepository;
    private final SupervisorRepository supervisorRepository;
    private final AdminRepository adminRepository;

    public AccountInfoService(UserRepository userRepository, SessionRepository sessionRepository,
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
        return counsellorRepository.findByEmail(email);
    }

    /**
     * 根据用户email获取监督员信息
     * @param email 监督员email
     * @return User 监督员信息
     */
    public Supervisor getSupervisorByEmail(String email) {
        // 从数据库中获取用户信息，并检查用户是否存在
        // 用户不存在，
        return supervisorRepository.findByEmail(email);
    }

    /**
     * 根据用户email获取管理员信息
     * @param email 管理员email
     * @return User 管理员信息
     */
    public Admin getAdminByEmail(String email) {
        // 从数据库中获取用户信息，并检查用户是否存在
        // 用户不存在，
        return adminRepository.findByEmail(email);
    }

    /**
     * 编辑用户信息
     * @param editRequestDto 用户编辑请求数据传输对象
     */
    public boolean editUserProfile(EditRequestDto editRequestDto) {
        try {
            Long uid = editRequestDto.getUid();
            // 根据uid获取用户实体
            User user = userRepository.findById(uid).orElseThrow(() -> new RuntimeException("User not found"));
            Account account = accountRepository.findById(uid).orElseThrow(() -> new RuntimeException("Account not found"));

            // 更新不为空的字段
            if (editRequestDto.getGender() != null && !editRequestDto.getGender().isEmpty()) {
                String gender = editRequestDto.getGender();
                if(gender.equals("male")){
                    user.setGender(User.Gender.male);
                }else if(gender.equals("female")){
                    user.setGender(User.Gender.female);
                } else if (gender.equals("unknown")) {
                    user.setGender(User.Gender.unknown);
                }else if(gender.equals("gunship")){
                    user.setGender(User.Gender.gunship);
                }else {
                    return false;
                }
            }

            if (editRequestDto.getNickname() != null && !editRequestDto.getNickname().isEmpty()) {
                user.setNickname(editRequestDto.getNickname());
            }
            if (editRequestDto.getPassword() != null && !editRequestDto.getPassword().isEmpty()) {
                account.setPassword(editRequestDto.getPassword());
            }

            // 保存更新后的用户信息
            userRepository.save(user);
            accountRepository.save(account);

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 编辑咨询师信息
     * @param editRequestDto 用户编辑请求数据传输对象
     */
    public boolean editCounsellorProfile(EditRequestDto editRequestDto) {
        try {
            Long uid = editRequestDto.getUid();
            // 根据uid获取用户实体
            Counsellor counsellor = counsellorRepository.findById(uid).orElseThrow(() -> new RuntimeException("User not found"));
            Account account = accountRepository.findById(uid).orElseThrow(() -> new RuntimeException("Account not found"));

            // 更新不为空的字段
            if (editRequestDto.getGender() != null && !editRequestDto.getGender().isEmpty()) {
                String gender = editRequestDto.getGender();
                if(gender.equals("male")){
                    counsellor.setGender(Counsellor.Gender.male);
                }else if(gender.equals("female")){
                    counsellor.setGender(Counsellor.Gender.female);
                } else if (gender.equals("unknown")) {
                    counsellor.setGender(Counsellor.Gender.unknown);
                }else if(gender.equals("gunship")){
                    counsellor.setGender(Counsellor.Gender.gunship);
                }else {
                    return false;
                }
            }

            if (editRequestDto.getNickname() != null && !editRequestDto.getNickname().isEmpty()) {
                counsellor.setNickname(editRequestDto.getNickname());
            }
            if (editRequestDto.getPassword() != null && !editRequestDto.getPassword().isEmpty()) {
                account.setPassword(editRequestDto.getPassword());
            }

            // 保存更新后的用户信息
            counsellorRepository.save(counsellor);
            accountRepository.save(account);

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 编辑督导信息
     * @param editRequestDto 用户编辑请求数据传输对象
     */
    public boolean editSupervisorProfile(EditRequestDto editRequestDto) {
        try {
            Long uid = editRequestDto.getUid();
            // 根据uid获取用户实体
            Supervisor supervisor = supervisorRepository.findById(uid).orElseThrow(() -> new RuntimeException("User not found"));
            Account account = accountRepository.findById(uid).orElseThrow(() -> new RuntimeException("Account not found"));
            if (editRequestDto.getGender() != null && !editRequestDto.getGender().isEmpty()) {
                if(editRequestDto.getGender().equals("male")){
                    supervisor.setGender(Supervisor.Gender.male);
                }else if(editRequestDto.getGender().equals("female")){
                    supervisor.setGender(Supervisor.Gender.female);
                }else if(editRequestDto.getGender().equals("unknown")){
                    supervisor.setGender(Supervisor.Gender.unknown);
                }else if(editRequestDto.getGender().equals("gunship")){
                    supervisor.setGender(Supervisor.Gender.gunship);
                }else {
                   return false;
                }
            }

            if (editRequestDto.getNickname() != null && editRequestDto.getNickname() != ""){
                supervisor.setNickname(editRequestDto.getNickname());
            }

            if (editRequestDto.getPassword() != null && editRequestDto.getPassword() != ""){
                account.setPassword(editRequestDto.getPassword());
            }

            supervisorRepository.save(supervisor);
            accountRepository.save(account);

        }catch (RuntimeException ex){
            return false;
        }

        return true;
    }

}
