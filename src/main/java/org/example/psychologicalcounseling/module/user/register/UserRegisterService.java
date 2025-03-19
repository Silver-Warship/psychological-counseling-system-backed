package org.example.psychologicalcounseling.module.user.register;


import org.example.psychologicalcounseling.dto.UserDto;
import org.example.psychologicalcounseling.model.User;
import org.example.psychologicalcounseling.repository.UserRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


@Service
public class UserRegisterService {

    private final UserRepository userRepository;

    public UserRegisterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean registerUser(UserDto userDto) {
        try {
            // 将 UserDto 转换为 User
            User user = new User();
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            user.setNickname(userDto.getNickname());
            // 保存到数据库
            userRepository.save(user);
            // 保存成功
            return true;
        } catch (Exception e) {
            // 捕获数据库操作异常
            return false;
        }
    }

    public UserDto findByUid(Long uid) {
        User user = userRepository.findById(uid).orElse(null);
        if (user != null) {
            UserDto userDto = new UserDto();
            userDto.setEmail(user.getEmail());
            userDto.setPassword(user.getPassword());
            userDto.setNickname(user.getNickname());
            return userDto;
        }
        return null;
    }
}