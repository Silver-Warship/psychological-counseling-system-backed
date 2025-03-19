package org.example.psychologicalcounseling.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PullUnReceivedMessageRequest {
    private Long userID;
    private Long sessionID;
    // The maximum number of messages this user can receive
    // -1 means no limit
    // private int messageCnt;  // not used for now
}
