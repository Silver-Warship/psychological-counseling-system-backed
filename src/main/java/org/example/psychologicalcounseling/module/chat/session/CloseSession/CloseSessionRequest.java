package org.example.psychologicalcounseling.module.chat.session.CloseSession;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CloseSessionRequest {
    Long sessionID;
}
