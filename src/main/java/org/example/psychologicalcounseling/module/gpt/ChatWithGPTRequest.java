package org.example.psychologicalcounseling.module.gpt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatWithGPTRequest {
    private Long senderID;
    private String content;
    private Long timestamp;
}

