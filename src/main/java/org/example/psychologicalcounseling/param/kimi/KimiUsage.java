package org.example.psychologicalcounseling.param.kimi;

import lombok.Data;

@Data
public class KimiUsage {
    private int prompt_tokens;
    private int completion_tokens;
    private int total_tokens;
}
