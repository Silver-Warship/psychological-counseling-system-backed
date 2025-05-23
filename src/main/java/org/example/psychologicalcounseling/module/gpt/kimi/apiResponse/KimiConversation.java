package org.example.psychologicalcounseling.module.gpt.kimi.apiResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KimiConversation {
    private int index;
    private KimiMessage message;
    private String finish_reason;
}
