package org.example.psychologicalcounseling.module.user.login;

import com.mysql.cj.jdbc.SuspendableXAConnection;
import org.example.psychologicalcounseling.model.User;
import org.example.psychologicalcounseling.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserLoginService {

    @Autowired
    UserRepository userRepository;

    //检查传入的email和nickname进行匹配
    public boolean authenticate(String username, String password) {
        // 模拟从数据库中获取用户信息
        User user = userRepository.findByEmail(username);

        String storedUsername = user.getNickname(); // 从数据库获取的用户名
        String storedPassword = user.getPassword(); // 假设从数据库获取的加密密码

        // 检查用户名是否匹配
        if (!username.equals(storedUsername)) {
            System.out.println("Username does not match");
            return false;
        }
        //检查密码是否匹配
        if (!password.equals(storedPassword)){
            System.out.println("Password does not match");
            return false;
        }

        return true;
    }

    //检查数据库中是否有匹配的记录

    //生成token


}
