package org.example.psychologicalcounseling.module.helpRecord;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddHelpRecordDto {
    private Long counsellorID;
    private Long supervisorID;
    private Long userSessionID;
    private Long helpSessionID;
}
