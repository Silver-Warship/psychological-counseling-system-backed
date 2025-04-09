package org.example.psychologicalcounseling.module.consultant.getConsultantRecord;

import lombok.Data;

@Data
public class GetConsultantRecordRequest {
    private Long userID;
    private Long counsellorID;
    private Long startTimestamp;
    private Long endTimestamp;
}
