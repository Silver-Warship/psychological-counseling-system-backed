package org.example.psychologicalcounseling.controller;

import org.example.psychologicalcounseling.module.AdminManage.GetBindingCounsellor.GetBindingCounsellorResponse;
import org.example.psychologicalcounseling.module.AdminManage.GetBindingCounsellor.GetBindingCounsellorService;
import org.example.psychologicalcounseling.repository.AccountRepository;
import org.example.psychologicalcounseling.repository.AdminRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminManageController {
    private final AdminRepository adminRepository;
    private final GetBindingCounsellorService getBindingCounsellorService;


    public AdminManageController(AdminRepository adminRepository, GetBindingCounsellorService getBindingCounsellorService) {
        this.adminRepository = adminRepository;
        this.getBindingCounsellorService = getBindingCounsellorService;
    }


    @GetMapping("/api/getBindingCounsellor")
    public ResponseEntity<?> getBindingCounsellor(@RequestParam Long adminID) {
        // check if adminID is valid
        if (adminID == null || !adminRepository.existsById(adminID)) {
            return ResponseEntity.badRequest().body("Invalid admin ID");
        }

        return getBindingCounsellorService.getBindingCounsellor(adminID).buildResponse();
    }
}
