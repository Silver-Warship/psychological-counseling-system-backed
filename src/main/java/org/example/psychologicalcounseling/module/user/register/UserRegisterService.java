package org.example.psychologicalcounseling.module.user.register;


import org.example.psychologicalcounseling.dto.UserDto;
import org.example.psychologicalcounseling.model.User;
import org.example.psychologicalcounseling.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class UserRegisterService {

    private final UserRepository userRepository;

    public UserRegisterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(UserDto userDto) {
        //注册用户数据保存到数据库
        // 将 UserDto 转换为 User

        System.out.println("Registering user: " + userDto.getEmail());
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setNickname(userDto.getNickname());
        //user需要自动生成不重复的id

        // 保存到数据库
        userRepository.save(user);

        System.out.println("User registered: " + user.getUid());
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