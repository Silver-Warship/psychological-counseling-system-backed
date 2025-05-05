package org.example.psychologicalcounseling.controller;

import org.example.psychologicalcounseling.module.OrderManage.counsellorOrderManage.CancelCounsellorOrderRequest;
import org.example.psychologicalcounseling.module.OrderManage.counsellorOrderManage.CounsellorOrderManageService;
import org.example.psychologicalcounseling.module.OrderManage.counsellorOrderManage.UpdateCounsellorOrderRequest;
import org.example.psychologicalcounseling.repository.CounsellorArrangementRepository;
import org.example.psychologicalcounseling.repository.CounsellorRepository;
import org.example.psychologicalcounseling.utils.TimeStampUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CounsellorArrangementController {
    private final CounsellorRepository counsellorRepository;
    private final CounsellorOrderManageService counsellorOrderManageService;
    private final CounsellorArrangementRepository counsellorArrangementRepository;

    public CounsellorArrangementController(CounsellorRepository counsellorRepository,
                                           CounsellorOrderManageService counsellorOrderManageService,
                                           CounsellorArrangementRepository counsellorArrangementRepository) {
        this.counsellorRepository = counsellorRepository;
        this.counsellorOrderManageService = counsellorOrderManageService;
        this.counsellorArrangementRepository = counsellorArrangementRepository;
    }

    /**
     * Get the order of a specific counsellor
     * @param counsellorID the ID of the counsellor
     * @param startTimestamp the start timestamp of the order
     * @param endTimestamp the end timestamp of the order
     * @return the order of the counsellor
     */
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

    /**
     * Get the order of all counsellors
     * @param startTimestamp the start timestamp of the order
     * @param endTimestamp the end timestamp of the order
     * @return the order of all counsellors
     */
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

    /**
     * Add a new order for a specific counsellor
     * @param addRequest the request containing the order details
     * @return the response indicating the result of the operation
     */
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

    /**
     * Cancel an existing order for a specific counsellor
     * @param cancelRequest the request containing the order details to be cancelled
     * @return the response indicating the result of the operation
     */
    @PostMapping("/api/cancelCounsellorOrder")
    public ResponseEntity<?> cancelCounsellorOrder(@RequestBody CancelCounsellorOrderRequest cancelRequest) {
        if (cancelRequest.getArrangeIDs() == null || cancelRequest.getArrangeIDs().isEmpty()) {
            return ResponseEntity.badRequest().body("Counsellor order is empty");
        }

        // check if arrangeIDs are valid
        for (Long arrangeID : cancelRequest.getArrangeIDs()) {
            if (arrangeID == null || arrangeID < 0 || !counsellorArrangementRepository.existsById(arrangeID)) {
                return ResponseEntity.badRequest().body("Invalid arrange ID");
            }
        }

        this.counsellorOrderManageService.cancelCounsellorOrder(cancelRequest.getArrangeIDs());
        return ResponseEntity.accepted().body("Counsellor order updated successfully");
    }
}
