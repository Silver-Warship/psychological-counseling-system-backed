package org.example.psychologicalcounseling.module.consultant.getConsultationDuration;

import lombok.Data;

@Data
public class getConsultationDurationRequest {
    private Long userID;
    private Long counsellorID;
    private Long startTimestamp;
    private Long endTimestamp;
}
