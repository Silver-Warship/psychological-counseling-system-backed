package org.example.psychologicalcounseling.module.gpt.kimi.apiResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KimiResponse {
    private String id;
    private String object;
    private int created;
    private String model;
    private KimiConversation[] choices;
    private KimiUsage usage;
}