package org.example.psychologicalcounseling.controller;


import io.lettuce.core.dynamic.annotation.Param;
import lombok.Data;
import org.example.psychologicalcounseling.module.info.AccountDeleteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.parser.Entity;

@RestController
@RequestMapping("/api")
public class AccountDeleteController {

    final AccountDeleteService accountDeleteService;

    public AccountDeleteController(AccountDeleteService accountDeleteService) {
        this.accountDeleteService = accountDeleteService;
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteAccount(@Param("role") String role, @Param("uid") Long id) {

        boolean result = false;

        if (role.equals("user")) {
            result = accountDeleteService.deleteUser(id);
        } else if (role.equals("counsellor")) {
            result = accountDeleteService.deleteCounsellor(id);
        } else if (role.equals("supervisor")) {
           result  = accountDeleteService.deleteSupervisor(id);
        } else {
            return ResponseEntity.badRequest().body("Invalidrole");
        }

        if(result){
            return ResponseEntity.ok("Account deleted successfully");
        }else {
            return ResponseEntity.internalServerError().body("Failed to delete account");
        }
    }
}
