package org.example.psychologicalcounseling.module.consultant.getConsultationDuration;

import lombok.Setter;
import org.example.psychologicalcounseling.dto.ResponseBuilder;
import org.springframework.http.ResponseEntity;

@Setter
public class getConsultationDurationResponse extends ResponseBuilder {
    public long durations;

    public ResponseEntity<?> buildResponse() {
        if (code == 601) {
            return ResponseEntity.badRequest().body(this);
        } else {
            return ResponseEntity.ok(this);
        }
    }
}