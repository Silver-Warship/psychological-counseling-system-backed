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
    //检查传入的email和password是否存在且匹配
    public boolean authenticate(String email, String password) {

        //从数据库中获取用户信息，并检查用户是否存在
        User user = userRepository.findByEmail(email);
        if (user==null) {
            //System.out.println("User not exist");
            return false;
        }
        //检查密码是否匹配
        String storedPassword = user.getPassword();
        if (!password.equals(storedPassword)){
            //System.out.println("Password does not match");
            return false;
        }
        return true;
    }
}
