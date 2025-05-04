package org.example.psychologicalcounseling.controller;


import org.example.psychologicalcounseling.module.OrderManage.counsellorOrderManage.CancelCounsellorOrderRequest;
import org.example.psychologicalcounseling.module.OrderManage.counsellorOrderManage.CounsellorOrderManageService;
import org.example.psychologicalcounseling.module.OrderManage.counsellorOrderManage.UpdateCounsellorOrderRequest;
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
    public ResponseEntity<?> addCounsellorOrder(@RequestBody UpdateCounsellorOrderRequest addRequest) {
        if (addRequest.getCounsellors() == null || addRequest.getCounsellors().isEmpty()) {
            return ResponseEntity.badRequest().body("Counsellor order is empty");
        }

        this.counsellorOrderManageService.addCounsellorOrder(addRequest.getCounsellors());
        return ResponseEntity.accepted().body("Counsellor order updated successfully");
    }

    @PostMapping("/api/cancelCounsellorOrder")
    public ResponseEntity<?> cancelCounsellorOrder(@RequestBody CancelCounsellorOrderRequest cancelRequest) {
        if (cancelRequest.getArrangeIDs() == null || cancelRequest.getArrangeIDs().isEmpty()) {
            return ResponseEntity.badRequest().body("Counsellor order is empty");
        }

        this.counsellorOrderManageService.cancelCounsellorOrder(cancelRequest.getArrangeIDs());
        return ResponseEntity.accepted().body("Counsellor order updated successfully");
    }
}
