package org.example.psychologicalcounseling.controller;

import org.example.psychologicalcounseling.module.AdminManage.GetBindingCounsellor.GetBindingCounsellorService;
import org.example.psychologicalcounseling.module.AdminManage.orderManage.OrderManageService;
import org.example.psychologicalcounseling.repository.AdminRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminManageController {
    private final AdminRepository adminRepository;
    private final GetBindingCounsellorService getBindingCounsellorService;
    private final OrderManageService orderManageService;


    public AdminManageController(AdminRepository adminRepository, GetBindingCounsellorService getBindingCounsellorService, OrderManageService orderManageService) {
        this.adminRepository = adminRepository;
        this.getBindingCounsellorService = getBindingCounsellorService;
        this.orderManageService = orderManageService;
    }


    @GetMapping("/api/getBindingCounsellor")
    public ResponseEntity<?> getBindingCounsellor(@RequestParam Long adminID) {
        // check if adminID is valid
        if (adminID == null || adminID < 0 || !adminRepository.existsById(adminID)) {
            return ResponseEntity.badRequest().body("Invalid admin ID");
        }

        return getBindingCounsellorService.getBindingCounsellor(adminID).buildResponse();
    }

    @GetMapping("/api/getCounsellorOrder")
    public ResponseEntity<?> getCounsellorOrder(@RequestParam Long counsellorID) {
        // check if counsellorID is valid
        if (counsellorID == null || counsellorID < 0 || !adminRepository.existsById(counsellorID)) {
            return ResponseEntity.badRequest().body("Invalid counsellor ID");
        }

        return orderManageService.getCounsellorOrder(counsellorID).buildResponse();
    }
}
