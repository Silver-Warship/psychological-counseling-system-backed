package org.example.psychologicalcounseling.module.gpt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatWithGPTResponse {
    private String content;
    private long timestamp;
}
