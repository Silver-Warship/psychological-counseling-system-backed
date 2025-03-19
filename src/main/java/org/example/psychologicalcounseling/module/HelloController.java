package org.example.psychologicalcounseling.module;


import org.example.psychologicalcounseling.dto.UserDto;
import org.example.psychologicalcounseling.module.user.register.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class HelloController {

    @Autowired//自动插入
    private UserRegisterService userRegisterService;


    @GetMapping("/hi")
    public  ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }
}