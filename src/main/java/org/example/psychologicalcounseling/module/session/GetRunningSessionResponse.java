package org.example.psychologicalcounseling.module.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.psychologicalcounseling.dto.ResponseBuilder;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
@NoArgsConstructor
public class GetRunningSessionResponse extends ResponseBuilder {
    @Data
    public static class Session {
        Long sessionID;
        Long firstUserID;
        Long secondUserID;
    }
    public Session[] sessions;

    public ResponseEntity<?> buildResponse() {
        if (code == 404) {
            return ResponseEntity.badRequest().body(this);
        } else {
            return ResponseEntity.ok(this.sessions);
        }
    }
}
