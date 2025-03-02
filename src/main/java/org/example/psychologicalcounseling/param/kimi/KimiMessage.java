package org.example.psychologicalcounseling.param.kimi;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KimiMessage {
    private String role;
    private String content;
}
