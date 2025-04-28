package org.example.psychologicalcounseling.module.consultant.addConsultantRecord;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class addConsultantRecordDto {
    private Long counsellorID;
    private Long userID;
    private Long sessionID;
    private Float userRating;
    private String appraisal;
    private String counsellorAppraisal;
}
