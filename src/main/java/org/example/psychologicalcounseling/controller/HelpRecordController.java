package org.example.psychologicalcounseling.controller;


import org.example.psychologicalcounseling.module.helpRecord.AddHelpRecordDto;
import org.example.psychologicalcounseling.module.helpRecord.HelpRecordService;
import org.example.psychologicalcounseling.module.helpRecord.getHelpRecord.GetHelpRecordRequest;
import org.example.psychologicalcounseling.module.helpRecord.getHelpRecord.GetHelpRecordResponse;
import org.example.psychologicalcounseling.repository.CounsellorRepository;
import org.example.psychologicalcounseling.repository.SessionRepository;
import org.example.psychologicalcounseling.repository.SupervisorRepository;
import org.example.psychologicalcounseling.utils.TimeStampUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelpRecordController {
    private final CounsellorRepository counsellorRepository;
    private final SupervisorRepository supervisorRepository;
    private final SessionRepository sessionRepository;
    private final HelpRecordService helpRecordService;

    public HelpRecordController(CounsellorRepository counsellorRepository, SupervisorRepository supervisorRepository, SessionRepository sessionRepository, HelpRecordService helpRecordService) {
        this.counsellorRepository = counsellorRepository;
        this.supervisorRepository = supervisorRepository;
        this.sessionRepository = sessionRepository;
        this.helpRecordService = helpRecordService;
    }

    @PostMapping("/api/addHelpRecord")
    ResponseEntity<?> addHelpRecord(@RequestBody AddHelpRecordDto dto) {
        if (dto.getCounsellorID() == null || !counsellorRepository.existsById(dto.getCounsellorID())) {
            return ResponseEntity.badRequest().body("Counsellor ID is invalid");
        }

        if (dto.getSupervisorID() == null || !supervisorRepository.existsById(dto.getSupervisorID())) {
            return ResponseEntity.badRequest().body("Supervisor ID is invalid");
        }

        if (dto.getUserSessionID() == null || !sessionRepository.existsById(dto.getUserSessionID())) {
            return ResponseEntity.badRequest().body("User session ID is invalid");
        }

        if (dto.getHelpSessionID() == null || !sessionRepository.existsById(dto.getHelpSessionID())) {
            return ResponseEntity.badRequest().body("Help session ID is invalid");
        }

        if (helpRecordService.addHelpRecord(dto.getCounsellorID(), dto.getSupervisorID(), dto.getUserSessionID(), dto.getHelpSessionID())) {
            return ResponseEntity.ok("Help record added successfully");
        }
        return ResponseEntity.badRequest().body("Failed to add help record");
    }

    @GetMapping("/api/getHelpRecord")
    ResponseEntity<?> getHelpRecord(GetHelpRecordRequest request) {
        GetHelpRecordResponse response = new GetHelpRecordResponse();
        // check if startTimestamp and endTimestamp are present
        if (request.getStartTimestamp() == null) {
            request.setStartTimestamp(0L);
        }
        if (request.getEndTimestamp() == null) {
            request.setEndTimestamp(System.currentTimeMillis());
        }

        var timeStampError = TimeStampUtil.timestampBetweenCheck(request.getStartTimestamp(), request.getEndTimestamp());
        if (timeStampError.isPresent()) {
            response.setCode(601);
            response.setCodeMsg(timeStampError.get());
            return response.buildResponse();
        }
        if (request.getCounsellorID() == null || !counsellorRepository.existsById(request.getCounsellorID()) ) {
            if (request.getSupervisorID() == null || !supervisorRepository.existsById(request.getSupervisorID())) {
                return ResponseEntity.badRequest().body("Counsellor ID or Supervisor ID is required");
            } else {
                response.helpRecords = helpRecordService.getHelpRecordBySupervisor(request.getSupervisorID(), request.getStartTimestamp(), request.getEndTimestamp());
            }
        } else {
             response.helpRecords = helpRecordService.getHelpRecordByCounsellor(request.getCounsellorID(), request.getStartTimestamp(), request.getEndTimestamp());
        }

        return ResponseEntity.ok(response);
    }
}
