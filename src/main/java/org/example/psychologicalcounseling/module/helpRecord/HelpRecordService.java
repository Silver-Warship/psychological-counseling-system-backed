package org.example.psychologicalcounseling.module.helpRecord;

import org.example.psychologicalcounseling.model.HelpRecord;
import org.example.psychologicalcounseling.module.helpRecord.getHelpRecord.GetHelpRecordResponse;
import org.example.psychologicalcounseling.repository.CounsellorRepository;
import org.example.psychologicalcounseling.repository.HelpRecordRepository;
import org.example.psychologicalcounseling.repository.SupervisorRepository;
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

    /**
     * 适配 HelpRecord 对象为 GetHelpRecordResponse.HelpRecord 对象
     *
     * @param helpRecords List<HelpRecord> 对象列表
     * @return GetHelpRecordResponse.HelpRecord[] 对象数组
     */
    private GetHelpRecordResponse.HelpRecord[] _HelpRecordAdopter(List<HelpRecord> helpRecords) {
        // get all counsellor name from counsellorID
        List<String> allCounsellorNames = helpRecords.stream().map((record) -> counsellorRepository.findCounsellorNameByCounsellorID(record.getCounsellorID())).toList();
        // get all supervisor name from supervisorID
        List<String> allSupervisorNames = helpRecords.stream().map((record) -> supervisorRepository.findSupervisorNameBySupervisorID(record.getSupervisorID())).toList();
        // get all help session duration from helpSessionID
        List<Long> allHelpSessionDurations = helpRecords.stream().map((record) -> {
            // get the duration from the session table
            return helpRecordRepository.findHelpSessionDurationByHelpSessionID(record.getHelpSessionID());
        }).toList();
        // get all help session start time from helpSessionID
        List<Long> allHelpSessionStartTimes = helpRecords.stream().map((record) -> {
            // get the start time from the session table
            return helpRecordRepository.findHelpSessionStartTimestampByHelpSessionID(record.getHelpSessionID());
        }).toList();

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
            helpRecordList[i].setDuration(allHelpSessionDurations.get(i));
            helpRecordList[i].setStartTimestamp(allHelpSessionStartTimes.get(i));
        }
        return helpRecordList;
    }

    /**
     * 添加帮助记录
     * @param counsellorID  辅导员 ID
     * @param supervisorID  监督员 ID
     * @param userSessionID 用户会话 ID
     * @param helpSessionID 帮助会话 ID
     * @return boolean 是否添加成功
     */
    public boolean addHelpRecord(Long counsellorID, Long supervisorID, Long userSessionID, Long helpSessionID) {
        // if there is already a record with the same counsellorID, supervisorID, userSessionID and helpSessionID, return false
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

    /**
     * 获取帮助记录
     * @param counsellorID 辅导员 ID
     * @param startTimestamp 开始时间戳
     * @param endTimestamp 结束时间戳
     * @return GetHelpRecordResponse.HelpRecord[] 帮助记录数组
     */
    public GetHelpRecordResponse.HelpRecord[] getHelpRecordByCounsellor(Long counsellorID, Long startTimestamp, Long endTimestamp) {
        // Retrieve help records from the database
        List<HelpRecord> helpRecords = helpRecordRepository.findByCounsellorIDAndStartTimeAndEndTime(counsellorID, startTimestamp, endTimestamp);

        return _HelpRecordAdopter(helpRecords);
    }

    /**
     * 获取帮助记录
     * @param supervisorID 监督员 ID
     * @param startTimestamp 开始时间戳
     * @param endTimestamp 结束时间戳
     * @return GetHelpRecordResponse.HelpRecord[] 帮助记录数组
     */
    public GetHelpRecordResponse.HelpRecord[] getHelpRecordBySupervisor(Long supervisorID, Long startTimestamp, Long endTimestamp) {
        // Retrieve help records from the database
        List<HelpRecord> helpRecords = helpRecordRepository.findBySupervisorIDAndStartTimeAndEndTime(supervisorID, startTimestamp, endTimestamp);

        return _HelpRecordAdopter(helpRecords);
    }
}
