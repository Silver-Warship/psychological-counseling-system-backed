package org.example.psychologicalcounseling.controller;

import org.example.psychologicalcounseling.module.OrderManage.counsellorOrderManage.UpdateConsellorOrderRequest;
import org.example.psychologicalcounseling.module.OrderManage.counsellorOrderManage.CounsellorOrderManageService;
import org.example.psychologicalcounseling.repository.CounsellorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CounsellorArrangementController {
    private final CounsellorRepository counsellorRepository;
    private final CounsellorOrderManageService counsellorOrderManageService;

    public CounsellorArrangementController(CounsellorRepository counsellorRepository, CounsellorOrderManageService counsellorOrderManageService) {
        this.counsellorRepository = counsellorRepository;
        this.counsellorOrderManageService = counsellorOrderManageService;
    }

    @GetMapping("/api/getCounsellorOrder")
    public ResponseEntity<?> getCounsellorOrder(@RequestParam Long counsellorID) {
        // check if counsellorID is valid
        if (counsellorID == null || counsellorID < 0 || !counsellorRepository.existsById(counsellorID)) {
            return ResponseEntity.badRequest().body("Invalid counsellor ID");
        }

        return counsellorOrderManageService.getCounsellorOrder(counsellorID).buildResponse();
    }

    @PostMapping("/api/addCounsellorOrder")
    public ResponseEntity<?> addCounsellorOrder(@RequestBody UpdateConsellorOrderRequest addRequest) {
        this.counsellorOrderManageService.addCounsellorOrder(addRequest.getConunsellors());
        return ResponseEntity.accepted().body("Counsellor order updated successfully");
    }

    @PostMapping("/api/cancelCounsellorOrder")
    public ResponseEntity<?> cancelCounsellorOrder(@RequestBody UpdateConsellorOrderRequest cancelRequest) {
        this.counsellorOrderManageService.cancelCounsellorOrder(cancelRequest.getConunsellors());
        return ResponseEntity.accepted().body("Counsellor order updated successfully");
    }

}
