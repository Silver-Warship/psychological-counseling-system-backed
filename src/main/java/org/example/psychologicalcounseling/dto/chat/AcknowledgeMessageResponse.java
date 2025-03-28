package org.example.psychologicalcounseling.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcknowledgeMessageResponse {
    private Long[] acknowledgedMessageIDs;
}
