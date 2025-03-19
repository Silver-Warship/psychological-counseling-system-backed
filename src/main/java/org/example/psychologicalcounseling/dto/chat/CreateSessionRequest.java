package org.example.psychologicalcounseling.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSessionRequest {
    private Long firstUserID;
    private Long secondUserID;
    private Long sessionStartTime;
}
