package org.example.psychologicalcounseling.module.session.GetRunningSession;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.psychologicalcounseling.dto.ResponseBuilder;
import org.springframework.http.ResponseEntity;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetRunningSessionNumberResponse extends ResponseBuilder {
    public Long number;

    public ResponseEntity<?> buildResponse() {
        if (code == 404) {
            return ResponseEntity.badRequest().body(this);
        } else {
            return ResponseEntity.ok(this);
        }
    }
}
