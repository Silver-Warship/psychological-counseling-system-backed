package org.example.psychologicalcounseling.controller;

import org.example.psychologicalcounseling.module.AdminManage.GetBindingCounsellor.GetBindingCounsellorService;
import org.example.psychologicalcounseling.module.AdminManage.orderManage.OrderManageService;
import org.example.psychologicalcounseling.repository.SupervisorManageRepository;
import org.example.psychologicalcounseling.repository.SupervisorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SupervisorManageController {
    private final SupervisorRepository supervisorRepository;
    private final GetBindingCounsellorService getBindingCounsellorService;
    private final OrderManageService orderManageService;


    public SupervisorManageController(SupervisorRepository supervisorRepository, GetBindingCounsellorService getBindingCounsellorService, OrderManageService orderManageService) {
        this.supervisorRepository = supervisorRepository;
        this.getBindingCounsellorService = getBindingCounsellorService;
        this.orderManageService = orderManageService;
    }


    @GetMapping("/api/getBindingCounsellor")
    public ResponseEntity<?> getBindingCounsellor(@RequestParam Long supervisorID) {
        // check if supervisorID is valid
        if (supervisorID == null || supervisorID < 0 || !supervisorRepository.existsById(supervisorID)) {
            return ResponseEntity.badRequest().body("Invalid admin ID");
        }

        return getBindingCounsellorService.getBindingCounsellor(supervisorID).buildResponse();
    }
}
