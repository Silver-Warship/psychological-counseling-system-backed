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
}
