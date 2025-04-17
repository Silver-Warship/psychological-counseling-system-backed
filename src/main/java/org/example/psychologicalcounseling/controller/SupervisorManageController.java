package org.example.psychologicalcounseling.controller;

import org.example.psychologicalcounseling.module.OrderManage.getBindingCounsellor.GetBindingCounsellorService;
import org.example.psychologicalcounseling.module.OrderManage.counsellorOrderManage.CounsellorOrderManageService;
import org.example.psychologicalcounseling.repository.SupervisorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SupervisorManageController {
    private final SupervisorRepository supervisorRepository;
    private final GetBindingCounsellorService getBindingCounsellorService;

    public SupervisorManageController(SupervisorRepository supervisorRepository, GetBindingCounsellorService getBindingCounsellorService, CounsellorOrderManageService counsellorOrderManageService) {
        this.supervisorRepository = supervisorRepository;
        this.getBindingCounsellorService = getBindingCounsellorService;
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
