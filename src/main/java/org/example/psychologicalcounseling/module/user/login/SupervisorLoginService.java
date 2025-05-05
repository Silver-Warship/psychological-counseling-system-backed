package org.example.psychologicalcounseling.module.user.login;

import org.example.psychologicalcounseling.dto.UserDto;
import org.example.psychologicalcounseling.model.Account;
import org.example.psychologicalcounseling.model.Supervisor;
import org.example.psychologicalcounseling.module.mail.SendMail;
import org.example.psychologicalcounseling.repository.AccountRepository;
import org.example.psychologicalcounseling.repository.SupervisorRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
public class SupervisorLoginService {
    private final SupervisorRepository supervisorRepository;
    private final AccountRepository accountRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private  final SendMail sendMail = SendMail.getInstance();

    public SupervisorLoginService(SupervisorRepository supervisorRepository, AccountRepository accountRepository,
                                  RedisTemplate<String, String> redisTemplate) {
        this.supervisorRepository = supervisorRepository;
        this.accountRepository = accountRepository;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 发送验证码到指定邮箱
     * @param email 邮箱地址
     * @param verificationCode 验证码
     * @return true表示发送成功，false表示发送失败或邮箱已存在
     */
    public Boolean SendVerificationCode(String email, String verificationCode) {
        //check if the email is already in Redis
        //System.out.println("查询哈希表");
        try {

            if (Boolean.TRUE.equals(redisTemplate.hasKey(email))) {
                System.out.println("查询哈希表发现重复");
                return false;
            }else{
                System.out.println("查询哈希表发现没有重复");
                sendMail.sendVerificationEmail(email, verificationCode);
                redisTemplate.opsForValue().set(email, verificationCode, 5, TimeUnit.MINUTES);
                return true;
            }
        } catch (Exception e) {
            // 捕获并打印异常信息
            System.err.println("在发送验证码或操作Redis时发生错误: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 检查验证码是否匹配
     * @param email 邮箱地址
     * @param verificationCode 验证码
     * @return true表示验证码匹配，false表示验证码不匹配或已过期
     */
    public boolean checkVerificationCode(String email, String verificationCode) {
        //从Redis中获取验证码
        String storedVerificationCode = redisTemplate.opsForValue().get(email);
        //检查验证码是否匹配
        if (storedVerificationCode == null || !storedVerificationCode.equals(verificationCode)) {
            //System.out.println("Verification code does not match");
            return false;
        }
        return true;
    }


    /**
     * 检查传入的email是否存在
     * @param email 邮箱地址
     * @return true表示邮箱存在，false表示邮箱不存在
     */
    public boolean checkEmail(String email) {
        //从数据库中获取用户信息，并检查用户是否存在
        Supervisor supervisor = supervisorRepository.findByEmail(email);
        //System.out.println("User not exist");
        return supervisor != null;
    }

    /**
     * 检查传入的email和password是否匹配
     * @param email 邮箱地址
     * @param password 密码
     * @return true表示密码匹配，false表示密码不匹配
     */
    public boolean checkPassword(String email, String password) {

        //从数据库中获取用户信息，并检查用户是否存在
        Supervisor supervisor = supervisorRepository.findByEmail(email);
        Long aid = supervisor.getSupervisorID();
        String storedPassword = accountRepository.findPasswordByAid(aid);
        //检查密码是否匹配
        //String storedPassword = user.getPassword();
        if (!password.equals(storedPassword)){
            System.out.println("Password does not match");
            return false;
        }
        return true;
    }

    /**
     * 添加用户
     * @param userDto 用户信息
     */
    public void addUser(UserDto userDto) {
        try {

            // 创建 Account 对象并设置 password
            Account account = new Account();
            account.setPassword(userDto.getPassword());

            // 保存到 account 表，获取自增的 aid
            accountRepository.save(account);
            Long aid = account.getAid(); // 假设 aid 是 int 类型

            // 将 UserDto 转换为 User
            Supervisor supervisor = new Supervisor();
            supervisor.setEmail(userDto.getEmail());
            //user.setPassword(userDto.getPassword());
            supervisor.setNickname(userDto.getNickname());
            supervisor.setSupervisorID(aid);
            // 保存到数据库
            supervisorRepository.save(supervisor);
            // 保存成功
        } catch (Exception e) {
            // 捕获数据库操作异常
            System.out.println("保存用户失败");
        }
    }
}
