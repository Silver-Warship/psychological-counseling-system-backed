package org.example.psychologicalcounseling.module.helpRecord;

import org.example.psychologicalcounseling.model.HelpRecord;
import org.example.psychologicalcounseling.repository.HelpRecordRepository;
import org.springframework.stereotype.Service;

@Service
public class HelpRecordService {
    private final HelpRecordRepository helpRecordRepository;

    public HelpRecordService(HelpRecordRepository helpRecordRepository) {
        this.helpRecordRepository = helpRecordRepository;
    }

    public void addHelpRecord(Long counsellorID, Long supervisorID, Long userSessionID, Long helpSessionID) {
        HelpRecord helpRecord = new HelpRecord();
        helpRecord.setCounsellorID(counsellorID);
        helpRecord.setSupervisorID(supervisorID);
        helpRecord.setUserSessionID(userSessionID);
        helpRecord.setHelpSessionID(helpSessionID);

        // Save the help record to the database
        helpRecordRepository.save(helpRecord);
    }

}
