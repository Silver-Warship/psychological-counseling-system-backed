package org.example.psychologicalcounseling.module.session.GetSessionMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.psychologicalcounseling.dto.ResponseBuilder;

@Setter
public class GetSessionMessageResponse extends ResponseBuilder {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static public class Message {
        Long sendID;
        Long receiveID;
        String content;
        String contentType;
        Long timestamp;
    }

    public Message[] messages;
}
