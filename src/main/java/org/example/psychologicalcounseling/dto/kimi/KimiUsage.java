package org.example.psychologicalcounseling.dto.kimi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KimiUsage {
    private int prompt_tokens;
    private int completion_tokens;
    private int total_tokens;
}
