package org.example.psychologicalcounseling.controller;


import org.example.psychologicalcounseling.module.info.AccountDeleteService;
import org.example.psychologicalcounseling.module.info.DeleteRoleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccountDeleteController {
    final AccountDeleteService accountDeleteService;

    public AccountDeleteController(AccountDeleteService accountDeleteService) {
        this.accountDeleteService = accountDeleteService;
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteAccount(@RequestBody DeleteRoleDto request) {
        boolean result = false;
        if (request.getRole() == null || request.getUid() == null) {
            return ResponseEntity.badRequest().body("Invalid parameters");
        }

        switch (request.getRole()) {
            case "user" -> result = accountDeleteService.deleteUser(request.getUid());
            case "counsellor" -> result = accountDeleteService.deleteCounsellor(request.getUid());
            case "supervisor" -> result = accountDeleteService.deleteSupervisor(request.getUid());
            default -> {
                return ResponseEntity.badRequest().body("Invalidrole");
            }
        }

        if(result){
            return ResponseEntity.ok("Account deleted successfully");
        }
        return ResponseEntity.internalServerError().body("Failed to delete account");
    }
}
