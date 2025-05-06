package org.example.psychologicalcounseling.controller;

import org.example.psychologicalcounseling.model.Account;
import org.example.psychologicalcounseling.model.Supervisor;
import org.example.psychologicalcounseling.module.addRole.AddRoleDto;
import org.example.psychologicalcounseling.repository.AccountRepository;
import org.example.psychologicalcounseling.repository.AdminRepository;
import org.example.psychologicalcounseling.repository.CounsellorRepository;
import org.example.psychologicalcounseling.repository.SupervisorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.example.psychologicalcounseling.module.addRole.AddRoleService;

@RestController
public class AddRoleController {
    private final AddRoleService addRoleService;

    public AddRoleController(AddRoleService addRoleService) {
        this.addRoleService = addRoleService;
    }

    @PostMapping("/api/addCounsellor")
    ResponseEntity<?> addCounsellor(@RequestBody AddRoleDto request) {
        if (!addRoleService.addCounsellor(request.getName(), request.getEmail(), request.getGender())) {
            return ResponseEntity.badRequest().body("Failed to add counsellor");
        }

        return ResponseEntity.ok("Counsellor added successfully");
    }

    @PostMapping("/api/addSupervisor")
    ResponseEntity<?> addSupervisor(@RequestBody AddRoleDto request) {
        if (!addRoleService.addSupervisor(request.getName(), request.getEmail(), request.getGender())) {
            return ResponseEntity.badRequest().body("Failed to add counsellor");
        }

        return ResponseEntity.ok("Counsellor added successfully");
    }

    @PostMapping("/api/addAdmin")
    ResponseEntity<?> addAdmin(@RequestBody AddRoleDto request) {
        if (!addRoleService.addAdmin(request.getName(), request.getEmail(), request.getGender())) {
            return ResponseEntity.badRequest().body("Failed to add counsellor");
        }

        return ResponseEntity.ok("Counsellor added successfully");
    }
}
