package org.example.psychologicalcounseling.module.user.register;


import org.example.psychologicalcounseling.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserRegisterController {

    @Autowired//自动插入
    private UserRegisterService userRegisterService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser( @RequestBody UserDto userDto) {
        userRegisterService.registerUser(userDto);
        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("/{uid}")
    public  ResponseEntity<UserDto> getUserByUid(@PathVariable Long uid) {
        UserDto userDto = userRegisterService.findByUid(uid);
        return ResponseEntity.ok(userDto);
    }
}