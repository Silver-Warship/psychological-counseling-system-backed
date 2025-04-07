package org.example.psychologicalcounseling.module.mail;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SendMailController {

    private final SendMail sendMail =SendMail.getInstance();

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
