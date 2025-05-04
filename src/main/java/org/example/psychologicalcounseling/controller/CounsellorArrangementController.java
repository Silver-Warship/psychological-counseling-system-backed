package org.example.psychologicalcounseling.controller;


import org.example.psychologicalcounseling.module.OrderManage.counsellorOrderManage.CancelCounsellorOrderRequest;
import org.example.psychologicalcounseling.module.OrderManage.counsellorOrderManage.CounsellorOrderManageService;
import org.example.psychologicalcounseling.module.OrderManage.counsellorOrderManage.UpdateCounsellorOrderRequest;
import org.example.psychologicalcounseling.repository.CounsellorRepository;
import org.example.psychologicalcounseling.utils.TimeStampUtil;
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
    public ResponseEntity<?> getCounsellorOrder(@RequestParam Long counsellorID, Long startTimestamp, Long endTimestamp) {
        if (startTimestamp == null || startTimestamp < 0) {
            startTimestamp = 0L;
        }
        if (endTimestamp == null || endTimestamp < 0) {
            endTimestamp = System.currentTimeMillis();
        }

        // check if startTimestamp and endTimestamp are valid
        var timeStampError = TimeStampUtil.timestampBetweenCheck(startTimestamp, endTimestamp);
        if (timeStampError.isPresent()) {
            return ResponseEntity.badRequest().body(timeStampError);
        }

        // check if counsellorID is valid
        if (counsellorID == null || counsellorID < 0 || !counsellorRepository.existsById(counsellorID)) {
            return ResponseEntity.badRequest().body("Invalid counsellor ID");
        }

        return counsellorOrderManageService.getCounsellorOrder(counsellorID, startTimestamp, endTimestamp).buildResponse();
    }

    @GetMapping("/api/getAllCounsellorOrder")
    public ResponseEntity<?> getAllCounsellorOrder(Long startTimestamp, Long endTimestamp) {
        if (startTimestamp == null || startTimestamp < 0) {
            startTimestamp = 0L;
        }
        if (endTimestamp == null || endTimestamp < 0) {
            endTimestamp = System.currentTimeMillis();
        }

        // check if startTimestamp and endTimestamp are valid
        var timeStampError = TimeStampUtil.timestampBetweenCheck(startTimestamp, endTimestamp);
        if (timeStampError.isPresent()) {
            return ResponseEntity.badRequest().body(timeStampError);
        }

        return counsellorOrderManageService.getCounsellorOrder(startTimestamp, endTimestamp).buildResponse();
    }

    @PostMapping("/api/addCounsellorOrder")
    public ResponseEntity<?> addCounsellorOrder(@RequestBody UpdateCounsellorOrderRequest addRequest) {
        if (addRequest.getCounsellors() == null || addRequest.getCounsellors().isEmpty()) {
            return ResponseEntity.badRequest().body("Counsellor order is empty");
        }
        // check if startTimestamp and endTimestamp are valid
        for (UpdateCounsellorOrderRequest.CounsellorOrder counsellor : addRequest.getCounsellors()) {
            var timeStampError = TimeStampUtil.timestampBetweenCheck(counsellor.getStartTimestamp(), counsellor.getEndTimestamp());
            if (timeStampError.isPresent()) {
                return ResponseEntity.badRequest().body(timeStampError);
            }
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
