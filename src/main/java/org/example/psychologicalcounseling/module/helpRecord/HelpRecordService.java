package org.example.psychologicalcounseling.module.helpRecord;

import org.example.psychologicalcounseling.model.HelpRecord;
import org.example.psychologicalcounseling.module.helpRecord.getHelpRecord.GetHelpRecordResponse;
import org.example.psychologicalcounseling.repository.CounsellorRepository;
import org.example.psychologicalcounseling.repository.HelpRecordRepository;
import org.example.psychologicalcounseling.repository.SupervisorRepository;
import org.example.psychologicalcounseling.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HelpRecordService {
    private final HelpRecordRepository helpRecordRepository;
    private final CounsellorRepository counsellorRepository;
    private final SupervisorRepository supervisorRepository;


    public HelpRecordService(HelpRecordRepository helpRecordRepository, CounsellorRepository counsellorRepository, SupervisorRepository supervisorRepository) {
        this.helpRecordRepository = helpRecordRepository;
        this.counsellorRepository = counsellorRepository;
        this.supervisorRepository = supervisorRepository;
    }

    private GetHelpRecordResponse.HelpRecord[] _HelpRecordAdopter(List<HelpRecord> helpRecords) {
        // get all counsellor name from counsellorID
        List<String> allCounsellorNames = helpRecords.stream().map((record) -> counsellorRepository.findCounsellorNameByCounsellorID(record.getCounsellorID())).toList();
        // get all supervisor name from supervisorID
        List<String> allSupervisorNames = helpRecords.stream().map((record) -> supervisorRepository.findSupervisorNameBySupervisorID(record.getSupervisorID())).toList();


        GetHelpRecordResponse.HelpRecord[] helpRecordList = new GetHelpRecordResponse.HelpRecord[helpRecords.size()];
        for (int i = 0; i < helpRecords.size(); i++) {
            helpRecordList[i] = new GetHelpRecordResponse.HelpRecord();
            helpRecordList[i].setRecordID(helpRecords.get(i).getRecordID());
            helpRecordList[i].setCounsellorID(helpRecords.get(i).getCounsellorID());
            helpRecordList[i].setSupervisorID(helpRecords.get(i).getSupervisorID());
            helpRecordList[i].setUserSessionID(helpRecords.get(i).getUserSessionID());
            helpRecordList[i].setHelpSessionID(helpRecords.get(i).getHelpSessionID());
            helpRecordList[i].setCounsellorName(allCounsellorNames.get(i));
            helpRecordList[i].setSupervisorName(allSupervisorNames.get(i));
        }
        return helpRecordList;
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

    public GetHelpRecordResponse.HelpRecord[] getHelpRecordByCounsellor(Long counsellorID, Long startTimestamp, Long endTimestamp) {
        // Retrieve help records from the database
        List<HelpRecord> helpRecords = helpRecordRepository.findByCounsellorIDAndStartTimeAndEndTime(counsellorID, startTimestamp, endTimestamp);

        return _HelpRecordAdopter(helpRecords);
    }

    public GetHelpRecordResponse.HelpRecord[] getHelpRecordBySupervisor(Long supervisorID, Long startTimestamp, Long endTimestamp) {
        // Retrieve help records from the database
        List<HelpRecord> helpRecords = helpRecordRepository.findBySupervisorIDAndStartTimeAndEndTime(supervisorID, startTimestamp, endTimestamp);

        return _HelpRecordAdopter(helpRecords);
    }
}
