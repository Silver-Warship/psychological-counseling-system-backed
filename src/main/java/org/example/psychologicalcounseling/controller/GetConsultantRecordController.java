package org.example.psychologicalcounseling.controller;

import org.example.psychologicalcounseling.module.consultant.getCompletedConsultationNumber.GetCompletedConsultationNumberRequest;
import org.example.psychologicalcounseling.module.consultant.getConsultantRecord.GetConsultantRecordRequest;
import org.example.psychologicalcounseling.module.consultant.getConsultantRecord.GetConsultantRecordResponse;
import org.example.psychologicalcounseling.module.consultant.ConsultantRecordService;
import org.example.psychologicalcounseling.module.consultant.getConsultationDuration.getConsultationDurationRequest;
import org.example.psychologicalcounseling.module.consultant.getConsultationDuration.getConsultationDurationResponse;
import org.example.psychologicalcounseling.module.session.GetRunningSession.GetRunningSessionNumberResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetConsultantRecordController {
    private final ConsultantRecordService getConsultantRecordService;

    public GetConsultantRecordController(ConsultantRecordService getConsultantRecordService) {
        this.getConsultantRecordService = getConsultantRecordService;
    }

    @GetMapping("/api/getConsultantRecord")
    public ResponseEntity<?> getConsultantRecord(GetConsultantRecordRequest getConsultantRecordRequest) {
        GetConsultantRecordResponse response = new GetConsultantRecordResponse();
        // check if startTimestamp and endTimestamp are present
        if (getConsultantRecordRequest.getStartTimestamp() == null) {
            getConsultantRecordRequest.setStartTimestamp(0L);
        }
        if (getConsultantRecordRequest.getEndTimestamp() == null) {
            getConsultantRecordRequest.setEndTimestamp(System.currentTimeMillis());
        }

        // check if userID or counsellorID present
        // if both are present, use userID
        // if both are not present, return error
        if (getConsultantRecordRequest.getUserID() != null) {
            response. setConsultantRecords(getConsultantRecordService.getConsultantRecordByUser(getConsultantRecordRequest.getUserID(),
                    getConsultantRecordRequest.getStartTimestamp(), getConsultantRecordRequest.getEndTimestamp()));
        }  else if (getConsultantRecordRequest.getCounsellorID() != null) {
            response.setConsultantRecords(getConsultantRecordService.getConsultantRecordByCounsellor(getConsultantRecordRequest.getCounsellorID(),
                    getConsultantRecordRequest.getStartTimestamp(), getConsultantRecordRequest.getEndTimestamp()));
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

        // check if userID or counsellorID present
        // if both are present, use userID
        // if both are not present, return error
        if (request.getUserID() != null) {
            response.setNumber(getConsultantRecordService.getCompletedSessionNumberByUserID(request.getUserID(),
                    request.getStartTimestamp(), request.getEndTimestamp()));
        }  else if (request.getCounsellorID() != null) {
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

        // check if userID or counsellorID present
        // if both are present, use userID
        // if both are not present, return error
        if (request.getUserID() != null) {
            response.setDurations(getConsultantRecordService.getHistoricalConsultationDurationByUserID(request.getUserID(),
                    request.getStartTimestamp(), request.getEndTimestamp()));
        }  else if (request.getCounsellorID() != null) {
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
