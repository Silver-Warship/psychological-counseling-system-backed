package org.example.psychologicalcounseling.module.mail;

import org.springframework.stereotype.Service;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.Properties;


@Service
public class SendMail {
    // 邮箱服务器配置
    /*
    *
    private static final String SMTP_HOST = "smtp.santonin.top";
    private static final int SMTP_PORT = 465; // SMTP端口号
    private static final String USERNAME = "HuaShiXinTu@santonin.top";
    private static final String PASSWORD = "10225101Ecnu";
    *
    */
    private static String SMTP_HOST ;
    private static int SMTP_PORT ; // SMTP端口号
    private static String USERNAME ;
    private static String PASSWORD ;
    private static boolean AUTH;
    private static boolean SSL;
    private static SendMail sendmail = null;

    /**
     * 获取 SendMail 实例
     *
     * @return SendMail 实例
     */
    public static SendMail getInstance() {
        if (sendmail == null) {
            sendmail = new SendMail();
        }
        return sendmail;
    }

    /**
     * 私有构造函数，加载配置文件
     */
    private SendMail() {
        Properties configProps = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            configProps.load(input);
            // 读取配置
            SMTP_HOST = configProps.getProperty("mail.smtp.host");
            SMTP_PORT = Integer.parseInt(configProps.getProperty("mail.smtp.port"));
            USERNAME = configProps.getProperty("mail.smtp.username");
            PASSWORD = configProps.getProperty("mail.smtp.password");
            AUTH=Boolean.parseBoolean(configProps.getProperty("mail.smtp.auth"));
            SSL=Boolean.parseBoolean(configProps.getProperty("mail.smtp.ssl.enable"));
        } catch (Exception e) {
            System.err.println("加载配置文件失败: " + e.getMessage());
        }
    }

    /**
     * 发送验证邮件
     *
     * @param toEmail 收件人邮箱地址
     * @param verificationCode 验证码
     * @return 发送是否成功
     */
    public boolean sendVerificationEmail(String toEmail, String verificationCode) {
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", AUTH);
        props.put("mail.smtp.ssl.enable", SSL);

        // 创建会话
        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                    }
                });

        try {
            // 创建邮件消息
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME)); // 设置发件人
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail)); // 设置收件人
            message.setSubject("邮箱验证"); // 设置邮件主题
            message.setText("这是一份测试邮件，正在测试邮箱是否能够正常使用。在本次测试中，验证码是：" + verificationCode+
                    "通过核对验证码，我们可以确认当前的邮箱功能是否正常。如果不小心发到了无关人士的邮箱中，请正常删除邮件，谢谢。"); // 设置邮件内容

            // 发送邮件
            Transport.send(message);

            System.out.println("验证邮件已发送到: " + toEmail);

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("发送邮件时出错: " + e.getMessage());
            return false;
        }
        return true;
    }
}
