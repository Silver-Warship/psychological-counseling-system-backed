package org.example.psychologicalcounseling.module.helpRecord.getHelpRecord;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetHelpRecordRequest {
    private Long counsellorID;
    private Long supervisorID;
    private Long startTimestamp;
    private Long endTimestamp;
}
