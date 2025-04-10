package org.example.psychologicalcounseling.module.session.GetRunningSession;

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

        // the time of the session start
        Long startTimestamp;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Other {
            String avatar;
            String nickname;
        }

        Other other;
    }
    public Session[] sessions;

    public ResponseEntity<?> buildResponse() {
        if (code == 404) {
            return ResponseEntity.badRequest().body(this);
        } else {
            return ResponseEntity.ok(this);
        }
    }
}
