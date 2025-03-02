package org.example.psychologicalcounseling.param.kimi;

import lombok.Data;

@Data
public class KimiConversation {
    private int index;
    private KimiMessage message;
    private String finish_reason;
}
