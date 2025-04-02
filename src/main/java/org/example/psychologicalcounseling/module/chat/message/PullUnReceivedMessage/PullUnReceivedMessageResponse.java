package org.example.psychologicalcounseling.module.chat.message.PullUnReceivedMessage;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PullUnReceivedMessageResponse {
    @Data
    public static class Message {
        private Long messageID;
        private org.example.psychologicalcounseling.model.Message.FileType contentType;
        private String content;
        private long timestamp;
    }

    private Message[] messages;
}
