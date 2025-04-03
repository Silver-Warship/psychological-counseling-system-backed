package org.example.psychologicalcounseling.module.chat.message.acknowledgeMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcknowledgeMessageRequest {
    private Long[] messageIDs;
    private Long ackTimestamp;
}
