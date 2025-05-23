package org.example.psychologicalcounseling.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import java.util.Objects;

@Getter
@Setter
public abstract class ResponseBuilder {
    protected Integer code = 200; // 默认值
    protected String codeMsg = "Success"; // 默认值

    public ResponseEntity<?> buildResponse(){
        if (Objects.equals(codeMsg, "Success")) {
            return ResponseEntity.ok(this);
        } else {
            return ResponseEntity.badRequest().body(this);
        }
    };

}