package org.example.psychologicalcounseling.module.user.login;


import lombok.Getter;
import lombok.Setter;
import org.example.psychologicalcounseling.dto.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class LoginResponse extends ResponseBuilder {
    private String token=null;
    // Getter 和 Setter

    /*暂定code相关：
    * 600 成功发送验证码/成功登录/成功注册
    * 601 The email is empty.
    * 602 Login successfully.
    * 603 The email and password are fail to pair.
    *
    * */

    public ResponseEntity<?> buildResponse(){
        if (code==404) {
            return ResponseEntity.badRequest().body(this);
        } else {
            return ResponseEntity.ok(this);
        }
    };
}
