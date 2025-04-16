package org.example.psychologicalcounseling.module.consultant;

import org.example.psychologicalcounseling.model.ConsultantRecord;
import org.example.psychologicalcounseling.module.consultant.getConsultantRecord.GetConsultantRecordResponse;
import org.example.psychologicalcounseling.repository.ConsultantRecordRepository;
import org.example.psychologicalcounseling.repository.CounsellorRepository;
import org.example.psychologicalcounseling.repository.SessionRepository;
import org.example.psychologicalcounseling.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultantRecordService {
    private final ConsultantRecordRepository consultantRecordRepository;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final CounsellorRepository counsellorRepository;


    public ConsultantRecordService(ConsultantRecordRepository consultantRecordRepository, UserRepository userRepository, SessionRepository sessionRepository, CounsellorRepository counsellorRepository) {
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
        List<String> allUserNames = consultantRecords.stream().map((record) -> userRepository.findUserNameByUid(record.getUserID())).toList();
        List<String> allCounsellorNames = consultantRecords.stream().map((record) -> counsellorRepository.findCounsellorNameByCounsellorID(record.getCounsellorID())).toList();
        List<Long> allSessionStartTimestamp = sessionRepository.findSessionStartTimestampBySessionID(consultantRecords.stream().map(ConsultantRecord::getSessionID).toList());
        List<Long> allSessionDuration = sessionRepository.findSessionDurationBySessionID(consultantRecords.stream().map(ConsultantRecord::getSessionID).toList());

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
        List<ConsultantRecord> records = consultantRecordRepository.findByCounsellorIDAndTimestampBetween(counsellorID, startTimestamp, endTimestamp);
        return _recordAdopter(records);
    }

    /**
     * Retrieves the number of completed sessions for a specific user within a given timestamp range.
     *
     * @param userID          The ID of the user.
     * @param startTimestamp  The start timestamp for the query.
     * @param endTimestamp    The end timestamp for the query.
     * @return The number of completed sessions.
     */
    public Long getCompletedSessionNumberByUserID(Long userID, Long startTimestamp, Long endTimestamp) {
        return consultantRecordRepository.findCompletedConsultantNumberByUserIDAndTimestampBetween(userID, startTimestamp, endTimestamp);
    }

    /**
     * Retrieves the number of completed sessions for a specific counsellor.
     *
     * @param counsellorID    The ID of the counsellor.
     * @param startTimestamp  The start timestamp for the query.
     * @param endTimestamp    The end timestamp for the query.
     * @return The number of completed sessions.
     */
    public Long getCompletedSessionNumberByCounsellorID(Long counsellorID, Long startTimestamp, Long endTimestamp) {
        return consultantRecordRepository.findCompletedConsultantNumberByCounsellorIDAndTimestampBetween(counsellorID, startTimestamp, endTimestamp);
    }

    /**
     * Retrieves the historical consultation duration for a specific user.
     *
     * @param userID          The ID of the user.
     * @param startTimestamp  The start timestamp for the query.
     * @param endTimestamp    The end timestamp for the query.
     * @return The total consultation duration in seconds.
     */
    public Long getHistoricalConsultationDurationByUserID(Long userID, Long startTimestamp, Long endTimestamp) {
        return consultantRecordRepository.findHistoricalConsultationDurationByUserID(userID, startTimestamp, endTimestamp);
    }

    /**
     * Retrieves the historical consultation duration for a specific counsellor.
     *
     * @param counsellorID    The ID of the counsellor.
     * @param startTimestamp  The start timestamp for the query.
     * @param endTimestamp    The end timestamp for the query.
     * @return The total consultation duration in seconds.
     */
    public Long getHistoricalConsultationDurationByCounsellorID(Long counsellorID, Long startTimestamp, Long endTimestamp) {
        return consultantRecordRepository.findHistoricalConsultationDurationByCounsellorID(counsellorID, startTimestamp, endTimestamp);
    }
}
