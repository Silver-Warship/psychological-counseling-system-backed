package org.example.psychologicalcounseling.controller;

import org.example.psychologicalcounseling.module.consultant.getCompletedConsultationNumber.GetCompletedConsultationNumberRequest;
import org.example.psychologicalcounseling.module.consultant.getConsultantRecord.GetConsultantRecordRequest;
import org.example.psychologicalcounseling.module.consultant.getConsultantRecord.GetConsultantRecordResponse;
import org.example.psychologicalcounseling.module.consultant.ConsultantRecordService;
import org.example.psychologicalcounseling.module.consultant.getConsultationDuration.getConsultationDurationRequest;
import org.example.psychologicalcounseling.module.consultant.getConsultationDuration.getConsultationDurationResponse;
import org.example.psychologicalcounseling.module.session.GetRunningSession.GetRunningSessionNumberResponse;
import org.example.psychologicalcounseling.repository.CounsellorRepository;
import org.example.psychologicalcounseling.repository.UserRepository;
import org.example.psychologicalcounseling.utils.TimeStampUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsultantRecordController {
    private final ConsultantRecordService getConsultantRecordService;
    private final UserRepository userRepository;
    private final CounsellorRepository counsellorRepository;

    public ConsultantRecordController(ConsultantRecordService getConsultantRecordService, UserRepository userRepository, CounsellorRepository counsellorRepository) {
        this.getConsultantRecordService = getConsultantRecordService;
        this.userRepository = userRepository;
        this.counsellorRepository = counsellorRepository;
    }

    @GetMapping("/api/getConsultantRecord")
    public ResponseEntity<?> getConsultantRecord(GetConsultantRecordRequest request) {
        GetConsultantRecordResponse response = new GetConsultantRecordResponse();
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

        // check if userID or counsellorID present
        // if both are present, use userID
        // if both are not present, return error
        if (request.getUserID() != null) {
            if (!userRepository.existsById(request.getUserID())) {
                response.setCode(601);
                response.setCodeMsg("The userID is invalid.");
                return response.buildResponse();
            }
            response.setConsultantRecords(getConsultantRecordService.getConsultantRecordByUser(request.getUserID(),
                    request.getStartTimestamp(), request.getEndTimestamp()));
        }  else if (request.getCounsellorID() != null) {
            if (!counsellorRepository.existsById(request.getCounsellorID())) {
                response.setCode(601);
                response.setCodeMsg("The userID is invalid.");
                return response.buildResponse();
            }
            response.setConsultantRecords(getConsultantRecordService.getConsultantRecordByCounsellor(request.getCounsellorID(),
                    request.getStartTimestamp(), request.getEndTimestamp()));
        } else {
                response.setCode(601);
                response.setCodeMsg("The userID and counsellorID are empty.");
                return response.buildResponse();
        }

        return response.buildResponse();
    }

    @GetMapping("/api/getCompletedConsultationNumber")
    public ResponseEntity<?> getCompletedConsultationNumber(GetCompletedConsultationNumberRequest request) {
        GetRunningSessionNumberResponse response = new GetRunningSessionNumberResponse();
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

        // check if userID or counsellorID present
        // if both are present, use userID
        // if both are not present, return error
        if (request.getUserID() != null) {
            if (!userRepository.existsById(request.getUserID())) {
                response.setCode(601);
                response.setCodeMsg("The userID is invalid.");
                return response.buildResponse();
            }
            response.setNumber(getConsultantRecordService.getCompletedSessionNumberByUserID(request.getUserID(),
                    request.getStartTimestamp(), request.getEndTimestamp()));
        }  else if (request.getCounsellorID() != null) {
            if (!counsellorRepository.existsById(request.getCounsellorID())) {
                response.setCode(601);
                response.setCodeMsg("The userID is invalid.");
                return response.buildResponse();
            }
            response.setNumber(getConsultantRecordService.getCompletedSessionNumberByCounsellorID(request.getCounsellorID(),
                    request.getStartTimestamp(), request.getEndTimestamp()));
        } else {
                response.setCode(601);
                response.setCodeMsg("The userID and counsellorID are empty.");
                return response.buildResponse();
        }

        return response.buildResponse();
    }

    @GetMapping("/api/getHistoricalConsultationDuration")
    public ResponseEntity<?> getHistoricalConsultationDuration(getConsultationDurationRequest request) {
        getConsultationDurationResponse response = new getConsultationDurationResponse();
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

        // check if userID or counsellorID present
        // if both are present, use userID
        // if both are not present, return error
        if (request.getUserID() != null) {
            if (!userRepository.existsById(request.getUserID())) {
                response.setCode(601);
                response.setCodeMsg("The userID is invalid.");
                return response.buildResponse();
            }
            response.setDurations(getConsultantRecordService.getHistoricalConsultationDurationByUserID(request.getUserID(),
                    request.getStartTimestamp(), request.getEndTimestamp()));
        }  else if (request.getCounsellorID() != null) {
            if (!counsellorRepository.existsById(request.getCounsellorID())) {
                response.setCode(601);
                response.setCodeMsg("The userID is invalid.");
                return response.buildResponse();
            }
            response.setDurations(getConsultantRecordService.getHistoricalConsultationDurationByCounsellorID(request.getCounsellorID(),
                    request.getStartTimestamp(), request.getEndTimestamp()));
        } else {
                response.setCode(601);
                response.setCodeMsg("The userID and counsellorID are empty.");
                return response.buildResponse();
        }

        return response.buildResponse();
    }
}
