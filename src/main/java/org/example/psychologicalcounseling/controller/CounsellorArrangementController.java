package org.example.psychologicalcounseling.controller;

import org.example.psychologicalcounseling.module.OrderManage.orderManage.OrderManageService;
import org.example.psychologicalcounseling.repository.CounsellorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CounsellorArrangementController {
    private final CounsellorRepository counsellorRepository;
    private final OrderManageService orderManageService;

    public CounsellorArrangementController(CounsellorRepository counsellorRepository, OrderManageService orderManageService) {
        this.counsellorRepository = counsellorRepository;
        this.orderManageService = orderManageService;
    }

    @PostMapping("/api/addCounsellorOrder")
    ResponseEntity<?> addCounsellorOrder() {
        return ResponseEntity.ok("Counsellor order added successfully");
    }

    @GetMapping("/api/getCounsellorOrder")
    public ResponseEntity<?> getCounsellorOrder(@RequestParam Long counsellorID) {
        // check if counsellorID is valid
        if (counsellorID == null || counsellorID < 0 || !counsellorRepository.existsById(counsellorID)) {
            return ResponseEntity.badRequest().body("Invalid counsellor ID");
        }

        return orderManageService.getCounsellorOrder(counsellorID).buildResponse();
    }

    @PostMapping("/api/cancelCounsellorOrder")
    ResponseEntity<?> cancelCounsellorOrder() {
        return ResponseEntity.ok("Counsellor order cancelled successfully");
    }
}
