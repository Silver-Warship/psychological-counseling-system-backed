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

    public boolean addHelpRecord(Long counsellorID, Long supervisorID, Long userSessionID, Long helpSessionID) {
        if (helpRecordRepository.existsByAllAttributes(counsellorID, supervisorID, userSessionID, helpSessionID) > 0) {
            return false;
        }

        HelpRecord helpRecord = new HelpRecord();
        helpRecord.setCounsellorID(counsellorID);
        helpRecord.setSupervisorID(supervisorID);
        helpRecord.setUserSessionID(userSessionID);
        helpRecord.setHelpSessionID(helpSessionID);

        // Save the help record to the database
        helpRecordRepository.save(helpRecord);

        return true;
    }

}
