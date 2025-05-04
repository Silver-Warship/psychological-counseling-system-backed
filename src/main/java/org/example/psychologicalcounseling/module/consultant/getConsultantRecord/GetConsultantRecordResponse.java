package org.example.psychologicalcounseling.module.consultant.getConsultantRecord;

import lombok.Data;
import lombok.Setter;
import org.example.psychologicalcounseling.dto.ResponseBuilder;
import org.springframework.http.ResponseEntity;

@Setter
public class GetConsultantRecordResponse extends ResponseBuilder {
    @Data
    public static class ConsultantRecord {
        private Long userID;
        private String userName;
        private Long counsellorID;
        private String counsellorName;
        private Long timestamp;
        private Long sessionID;
        private Long duration;
        private Float userRating;
        private String appraisal;
        private String counsellorAppraisal;
    }

    public ConsultantRecord[] consultantRecords;

    public ResponseEntity<?> buildResponse() {
        if (code == 601) {
            return ResponseEntity.badRequest().body(this);
        } else {
            return ResponseEntity.ok(this);
        }
    }
}
