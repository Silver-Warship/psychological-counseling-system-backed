package org.example.psychologicalcounseling.controller;


import org.example.psychologicalcounseling.module.helpRecord.AddHelpRecordDto;
import org.example.psychologicalcounseling.module.helpRecord.HelpRecordService;
import org.example.psychologicalcounseling.repository.CounsellorRepository;
import org.example.psychologicalcounseling.repository.SessionRepository;
import org.example.psychologicalcounseling.repository.SupervisorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
