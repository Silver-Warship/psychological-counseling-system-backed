package org.example.psychologicalcounseling.controller;

import org.example.psychologicalcounseling.module.mail.SendMail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMailController {
    private final SendMail sendMail = SendMail.getInstance();

    /**
     * 发送验证邮件
     * @param toEmail        收件人邮箱地址
     * @param verificationCode 验证码
     * @return 发送结果
     */
    @GetMapping("/test/sendVerificationEmail")
    public String sendVerificationEmail(@RequestParam String toEmail, @RequestParam String verificationCode) {
        boolean isSuccess = sendMail.sendVerificationEmail(toEmail, verificationCode);
        if (isSuccess) {
            return "验证邮件已成功发送到: " + toEmail;
        } else {
            return "发送验证邮件失败，请检查日志以获取更多信息。";
        }
    }
}
