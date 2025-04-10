package org.example.psychologicalcounseling.module.consultant.getCompletedConsultationNumber;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCompletedConsultationNumberRequest {
    private Long userID;
    private Long counsellorID;
    private Long startTimestamp;
    private Long endTimestamp;
}
