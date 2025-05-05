package org.example.psychologicalcounseling.module.helpRecord.getHelpRecord;

import jakarta.persistence.Column;
import lombok.Data;
import org.example.psychologicalcounseling.dto.ResponseBuilder;
import org.springframework.http.ResponseEntity;

public class GetHelpRecordResponse extends ResponseBuilder {
    @Data
    public static class HelpRecord {
        private Long counsellorID;
        private String counsellorName;
        private Long recordID;
        private Long supervisorID;
        private String supervisorName;
        private Long userSessionID;
        private Long helpSessionID;
        private Long duration;
        private Long startTimestamp;
    }

    public GetHelpRecordResponse.HelpRecord[] helpRecords;

    public ResponseEntity<?> buildResponse() {
        if (code == 601) {
            return ResponseEntity.badRequest().body(this);
        } else {
            return ResponseEntity.ok(this);
        }
    }
}