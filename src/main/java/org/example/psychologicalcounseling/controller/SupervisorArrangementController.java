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

    public SupervisorArrangementController(SupervisorOrderManageService supervisorOrderManageService,
                                           SupervisorRepository supervisorRepository,
                                           SupervisorArrangementRepository supervisorArrangementRepository) {
        this.supervisorOrderManageService = supervisorOrderManageService;
        this.supervisorRepository = supervisorRepository;
        this.supervisorArrangementRepository = supervisorArrangementRepository;
    }

    /**
     * Get the order of a specific supervisor
     * @param supervisorID the ID of the supervisor
     * @param startTimestamp the start timestamp of the order
     * @param endTimestamp the end timestamp of the order
     * @return the order of the supervisor
     */
    @GetMapping("/api/getSupervisorOrder")
    public ResponseEntity<?> getSupervisorOrder(@RequestParam Long supervisorID, Long startTimestamp, Long endTimestamp) {
        // if the startTimestamp and endTimestamp are not present or illegal, set them to default values
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

    /**
     * Get the order of all supervisors
     * @param startTimestamp the start timestamp of the order
     * @param endTimestamp the end timestamp of the order
     * @return the order of all supervisors
     */
    @GetMapping("/api/getAllSupervisorOrder")
    public ResponseEntity<?> getAllSupervisorOrder(Long startTimestamp, Long endTimestamp) {
        // if the startTimestamp and endTimestamp are not present or illegal, set them to default values
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

    /**
     * Add a new order for a specific supervisor
     * @param request the request containing the supervisorID, startTimestamp and endTimestamp
     * @return the result of adding the order
     */
    @PostMapping("/api/addSupervisorOrder")
    public ResponseEntity<?> addSupervisorOrder(@RequestBody AddSupervisorOrderDto request) {
        // check if the supervisorID is valid
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
        if (supervisorOrderManageService.addSupervisorOrder(request.getSupervisorID(),
                request.getStartTimestamp(), request.getEndTimestamp())) {
            return ResponseEntity.ok("The order is added successfully");
        } else {
            return ResponseEntity.badRequest().body("add error, please contact the administrator");
        }
    }

    /**
     * Cancel a specific supervisor order
     * @param request the request containing the arrangementID
     * @return the result of cancelling the order
     */
    @PostMapping("/api/cancelSupervisorOrder")
    public ResponseEntity<?> cancelSupervisorOrder(@RequestBody CancelSupervisorOrderDto request) {
        // check if the arrangement id is valid
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
