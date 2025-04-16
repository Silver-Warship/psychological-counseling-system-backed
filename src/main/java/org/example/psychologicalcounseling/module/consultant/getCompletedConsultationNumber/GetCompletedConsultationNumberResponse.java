package org.example.psychologicalcounseling.module.consultant.getCompletedConsultationNumber;

import org.example.psychologicalcounseling.dto.ResponseBuilder;
import org.springframework.http.ResponseEntity;

public class GetCompletedConsultationNumberResponse extends ResponseBuilder {
    public Long number;

    public ResponseEntity<?> buildResponse() {
        if (code == 404) {
            return ResponseEntity.badRequest().body(this);
        } else {
            return ResponseEntity.ok(this);
        }
    }
}