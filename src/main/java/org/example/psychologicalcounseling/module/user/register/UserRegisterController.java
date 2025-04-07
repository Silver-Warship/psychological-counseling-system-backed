package org.example.psychologicalcounseling.module.user.register;


import org.example.psychologicalcounseling.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/test")
public class UserRegisterController {

    @Autowired//自动插入
    private UserRegisterService userRegisterService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser( @RequestBody UserDto userDto) {
        RegisterResponse response= new RegisterResponse();
        if (!userRegisterService.registerUser(userDto)){
            response.setCode(400);
            response.setCodeMsg("Repeated email.");
        }
        return response.buildResponse();
    }

    @GetMapping("/getUserBy/{uid}")
    public  ResponseEntity<UserDto> getUserByUid(@PathVariable int uid) {
        UserDto userDto = userRegisterService.findByUid(uid);
        return ResponseEntity.ok(userDto);
    }
}