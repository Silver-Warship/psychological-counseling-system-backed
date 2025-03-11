package org.example.psychologicalcounseling.dto.chat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransmitMessageRequest {
    private String sessionID;
    private String senderID;
    private String receiverID;
    private String content;
    private String messageType;
    private long timestamp;
}
