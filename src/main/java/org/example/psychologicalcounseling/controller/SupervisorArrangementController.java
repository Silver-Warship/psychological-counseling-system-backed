package org.example.psychologicalcounseling.controller;

import org.example.psychologicalcounseling.module.OrderManage.supervisorOrderManage.AddSupervisorOrderDto;
import org.example.psychologicalcounseling.module.OrderManage.supervisorOrderManage.CancelSupervisorOrderDto;
import org.example.psychologicalcounseling.module.OrderManage.supervisorOrderManage.SupervisorOrderManageService;
import org.example.psychologicalcounseling.repository.SupervisorArrangementRepository;
import org.example.psychologicalcounseling.repository.SupervisorRepository;
import org.example.psychologicalcounseling.utils.TimeStampUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SupervisorArrangementController {
    final private SupervisorOrderManageService supervisorOrderManageService;
    final private SupervisorRepository supervisorRepository;
    final private SupervisorArrangementRepository supervisorArrangementRepository;

    public SupervisorArrangementController(SupervisorOrderManageService supervisorOrderManageService, SupervisorRepository supervisorRepository, SupervisorArrangementRepository supervisorArrangementRepository) {
        this.supervisorOrderManageService = supervisorOrderManageService;
        this.supervisorRepository = supervisorRepository;
        this.supervisorArrangementRepository = supervisorArrangementRepository;
    }


    @GetMapping("/api/getSupervisorOrder")
    public ResponseEntity<?> getSupervisorOrder(@RequestParam Long supervisorID, Long startTimestamp, Long endTimestamp) {
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

        // get the supervisor order
        return supervisorOrderManageService.getSupervisorOrder(supervisorID, startTimestamp, endTimestamp).buildResponse();
    }

    @GetMapping("/api/getAllSupervisorOrder")
    public ResponseEntity<?> getAllSupervisorOrder(Long startTimestamp, Long endTimestamp) {
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

        // get the supervisor order
        return supervisorOrderManageService.getAllSupervisorOrder(startTimestamp, endTimestamp).buildResponse();
    }

    @PostMapping("/api/addSupervisorOrder")
    public ResponseEntity<?> addSupervisorOrder(@RequestBody AddSupervisorOrderDto request) {
        if (request.getSupervisorID() == null || !supervisorRepository.existsById(request.getSupervisorID())) {
            return ResponseEntity.badRequest().body("Invalid supervisor ID");
        }

        // check if startTimestamp and endTimestamp are valid
        var timeStampError = TimeStampUtil.timestampBetweenCheck(request.getStartTimestamp(), request.getEndTimestamp());
        if (timeStampError.isPresent()) {
            return ResponseEntity.badRequest().body(timeStampError);
        }

        // check whether the order is valid
        if (supervisorArrangementRepository.countBySupervisorIDAndStartTimestampBetween(request.getSupervisorID(),
                request.getStartTimestamp(), request.getEndTimestamp()) > 0) {
            return ResponseEntity.badRequest().body("The order is invalid（time conflict）, please check the time");
        }

        // add the order
        if (supervisorOrderManageService.addSupervisorOrder(request.getSupervisorID(), request.getStartTimestamp(), request.getEndTimestamp())) {
            return ResponseEntity.ok("The order is added successfully");
        } else {
            return ResponseEntity.badRequest().body("add error, please contact the administrator");
        }
    }

    @PostMapping("/api/cancelSupervisorOrder")
    public ResponseEntity<?> cancelSupervisorOrder(@RequestBody CancelSupervisorOrderDto request) {
        if (request.getArrangementID() == null) {
            return ResponseEntity.badRequest().body("Invalid parameters");
        }

        // check if the supervisorID is valid
        if (!supervisorArrangementRepository.existsById(request.getArrangementID())) {
            return ResponseEntity.badRequest().body("Invalid arrangement ID");
        }

        // cancel the order
        if (supervisorOrderManageService.removeSupervisorOrder(request.getArrangementID())) {
            return ResponseEntity.ok("The order is cancelled successfully");
        } else {
            return ResponseEntity.badRequest().body("cancel error, please contact the administrator");
        }
    }
}
