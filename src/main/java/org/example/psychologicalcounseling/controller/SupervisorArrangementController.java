package org.example.psychologicalcounseling.controller;

import org.example.psychologicalcounseling.module.OrderManage.counsellorOrderManage.GetCounsellorOrderResponse;
import org.example.psychologicalcounseling.module.OrderManage.counsellorOrderManage.SupervisorOrderManageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SupervisorArrangementController {
    final private SupervisorOrderManageService supervisorOrderManageService;

    public SupervisorArrangementController(SupervisorOrderManageService supervisorOrderManageService) {
        this.supervisorOrderManageService = supervisorOrderManageService;
    }


    @GetMapping("/api/getSupervisorOrder")
    public ResponseEntity<?> getSupervisorOrder(Long supervisorID, Long startTimestamp, Long endTimestamp) {
        if (startTimestamp == null || startTimestamp < 0) {
            startTimestamp = 0L;
        }
        if (endTimestamp == null || endTimestamp < 0) {
            endTimestamp = System.currentTimeMillis();
        }

        // check whether the timestamp is valid
        if (startTimestamp > endTimestamp) {
            return ResponseEntity.badRequest().body("startTimestamp should be less than or equal endTimestamp");
        }

        // get the supervisor order
        return supervisorOrderManageService.getSupervisorOrder(supervisorID, startTimestamp, endTimestamp).buildResponse();
    }

    @PostMapping("/api/addSupervisorOrder")
    public ResponseEntity<?> addSupervisorOrder(int supervisorID) {
        return null;
    }

    @PostMapping("/api/cancelSupervisorOrder")
    public ResponseEntity<?> cancelSupervisorOrder(int supervisorID) {
        return null;
    }
}
