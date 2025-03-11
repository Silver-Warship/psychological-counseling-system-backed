package org.example.psychologicalcounseling.dto.kimi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KimiMessage {
    private String role;
    private String content;
}
