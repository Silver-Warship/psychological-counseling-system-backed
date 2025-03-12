package org.example.psychologicalcounseling.module.user.login;

import com.mysql.cj.jdbc.SuspendableXAConnection;
import org.example.psychologicalcounseling.model.User;
import org.example.psychologicalcounseling.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLoginService {

    @Autowired
    UserRepository userRepository;

    //检查传入的email和password进行匹配
    public boolean authenticate(String email, String password) {
        // 模拟从数据库中获取用户信息
        User user = userRepository.findByEmail(email);
        //如果user非空
        if (user==null) {
            System.out.println("User not exist");
            return false;
        }
        String storedPassword = user.getPassword(); // 假设从数据库获取的加密密码



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
