package org.example.psychologicalcounseling.module.chat.message.TransmitMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransmitMessageRequest {
    private Long sessionID;
    private Long senderID;
    private Long receiverID;
    private String content;
    private org.example.psychologicalcounseling.model.Message.FileType contentType;
    private Long timestamp;
}
