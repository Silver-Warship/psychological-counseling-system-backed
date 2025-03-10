package org.example.psychologicalcounseling.module.user;


import org.example.psychologicalcounseling.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired//自动插入
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser( @RequestBody UserDto userDto) {
        userService.registerUser(userDto);
        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("/{uid}")
    public  ResponseEntity<UserDto> getUserByUid(@PathVariable Long uid) {
        UserDto userDto = userService.findByUid(uid);
        return ResponseEntity.ok(userDto);
    }
}