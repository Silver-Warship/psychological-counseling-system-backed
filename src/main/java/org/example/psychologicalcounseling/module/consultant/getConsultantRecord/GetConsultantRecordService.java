package org.example.psychologicalcounseling.module.consultant.getConsultantRecord;

import org.example.psychologicalcounseling.model.ConsultantRecord;
import org.example.psychologicalcounseling.repository.ConsultantRecordRepository;
import org.example.psychologicalcounseling.repository.CounsellorRepository;
import org.example.psychologicalcounseling.repository.SessionRepository;
import org.example.psychologicalcounseling.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetConsultantRecordService {
    private final ConsultantRecordRepository consultantRecordRepository;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final CounsellorRepository counsellorRepository;


    public GetConsultantRecordService(ConsultantRecordRepository consultantRecordRepository, UserRepository userRepository, SessionRepository sessionRepository, CounsellorRepository counsellorRepository) {
        this.consultantRecordRepository = consultantRecordRepository;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.counsellorRepository = counsellorRepository;
    }


    /**
     * Adapts the ConsultantRecord entity to the response format.
     *
     * @param consultantRecords List of ConsultantRecord entities.
     * @return Array of adapted ConsultantRecord responses.
     */
    private GetConsultantRecordResponse.ConsultantRecord[] _recordAdopter(List<ConsultantRecord> consultantRecords) {
        // Create an array of ConsultantRecord responses
        GetConsultantRecordResponse.ConsultantRecord[] records = new GetConsultantRecordResponse.ConsultantRecord[consultantRecords.size()];
        // get username and counsellor name from userID and counsellorID
        List<String> allUserNames = userRepository.findAllUserNameByUid(consultantRecords.stream().map(ConsultantRecord::getUserID).toList());
        List<String> allCounsellorNames = counsellorRepository.findAllCounsellorNameByCounsellorID(consultantRecords.stream().map(ConsultantRecord::getCounsellorID).toList());
        List<Long> allSessionStartTimestamp = sessionRepository.getSessionStartTimestampBySessionID(consultantRecords.stream().map(ConsultantRecord::getSessionID).toList());
        List<Long> allSessionDuration = sessionRepository.getSessionDurationBySessionID(consultantRecords.stream().map(ConsultantRecord::getSessionID).toList());

        for (int i = 0; i < consultantRecords.size(); i++) {
            ConsultantRecord record = consultantRecords.get(i);
            GetConsultantRecordResponse.ConsultantRecord response = new GetConsultantRecordResponse.ConsultantRecord();
            response.setUserID(record.getUserID());
            response.setUserName(allUserNames.get(i));
            response.setCounsellorID(record.getCounsellorID());
            response.setCounsellorName(allCounsellorNames.get(i));
            response.setTimestamp(allSessionStartTimestamp.get(i));
            response.setDuration(allSessionDuration.get(i));
            response.setUserRating(record.getUserRating());
            response.setAppraisal(record.getAppraisal());
            records[i] = response;
        }


        return records;
    }

    /**
     * Retrieves consultant records for a specific user within a given timestamp range.
     *
     * @param userID          The ID of the user.
     * @param startTimestamp  The start timestamp for the query.
     * @param endTimestamp    The end timestamp for the query.
     * @return Array of ConsultantRecord responses.
     */
    public GetConsultantRecordResponse.ConsultantRecord[]  getConsultantRecordByUser(Long userID, Long startTimestamp, Long endTimestamp) {
        List<ConsultantRecord> records = consultantRecordRepository.findByUserIDAndTimestampBetween(userID, startTimestamp, endTimestamp);
        return _recordAdopter(records);
    }

    /**
     * Retrieves consultant records for a specific counsellor within a given timestamp range.
     *
     * @param counsellorID    The ID of the counsellor.
     * @param startTimestamp  The start timestamp for the query.
     * @param endTimestamp    The end timestamp for the query.
     * @return Array of ConsultantRecord responses.
     */
    public GetConsultantRecordResponse.ConsultantRecord[]  getConsultantRecordByCounsellor(Long counsellorID, Long startTimestamp, Long endTimestamp) {
        List<ConsultantRecord> records = consultantRecordRepository.findByUserIDAndTimestampBetween(counsellorID, startTimestamp, endTimestamp);
        return _recordAdopter(records);
    }
}
