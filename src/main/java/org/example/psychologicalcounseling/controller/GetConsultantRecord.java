package org.example.psychologicalcounseling.controller;

import org.example.psychologicalcounseling.module.consultant.getConsultantRecord.GetConsultantRecordRequest;
import org.example.psychologicalcounseling.module.consultant.getConsultantRecord.GetConsultantRecordResponse;
import org.example.psychologicalcounseling.module.consultant.getConsultantRecord.GetConsultantRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetConsultantRecord {
    private final GetConsultantRecordService getConsultantRecordService;

    public GetConsultantRecord(GetConsultantRecordService getConsultantRecordService) {
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

}
