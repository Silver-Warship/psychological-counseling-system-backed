package org.example.psychologicalcounseling.controller;

import org.example.psychologicalcounseling.module.AdminManage.orderManage.OrderManageService;
import org.example.psychologicalcounseling.module.AdminManage.orderManage.updateConsellorOrder.UpdateConsellorOrderRequest;
import org.example.psychologicalcounseling.repository.CounsellorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CounsellorArrangementController {
    private final CounsellorRepository counsellorRepository;
    private final OrderManageService orderManageService;

    public CounsellorArrangementController(CounsellorRepository counsellorRepository, OrderManageService orderManageService) {
        this.counsellorRepository = counsellorRepository;
        this.orderManageService = orderManageService;
    }

    @GetMapping("/api/getCounsellorOrder")
    public ResponseEntity<?> getCounsellorOrder(@RequestParam Long counsellorID) {
        // check if counsellorID is valid
        if (counsellorID == null || counsellorID < 0 || !counsellorRepository.existsById(counsellorID)) {
            return ResponseEntity.badRequest().body("Invalid counsellor ID");
        }

        return orderManageService.getCounsellorOrder(counsellorID).buildResponse();
    }

    @PostMapping("/api/addCounsellorOrder")
    public ResponseEntity<?> addCounsellorOrder(@RequestBody UpdateConsellorOrderRequest addRequest) {
        this.orderManageService.addCounsellorOrder(addRequest.getConunsellors());
        return ResponseEntity.accepted().body("Counsellor order updated successfully");
    }

    @PostMapping("/api/cancelCounsellorOrder")
    public ResponseEntity<?> cancelCounsellorOrder(@RequestBody UpdateConsellorOrderRequest cancelRequest) {
        this.orderManageService.cancelCounsellorOrder(cancelRequest.getConunsellors());
        return ResponseEntity.accepted().body("Counsellor order updated successfully");
    }

}
