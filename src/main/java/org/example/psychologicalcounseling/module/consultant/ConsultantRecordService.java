package org.example.psychologicalcounseling.module.consultant;

import org.example.psychologicalcounseling.model.ConsultantRecord;
import org.example.psychologicalcounseling.model.Message;
import org.example.psychologicalcounseling.model.Session;
import org.example.psychologicalcounseling.module.consultant.getConsultantRecord.GetConsultantRecordResponse;
import org.example.psychologicalcounseling.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.FrameworkServlet;

import java.util.List;
import java.util.Objects;

@Service
public class ConsultantRecordService {
    private final ConsultantRecordRepository consultantRecordRepository;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final CounsellorRepository counsellorRepository;
    private final MessageRepository messageRepository;


    public ConsultantRecordService(ConsultantRecordRepository consultantRecordRepository, UserRepository userRepository,
                                   SessionRepository sessionRepository, CounsellorRepository counsellorRepository,
                                   MessageRepository messageRepository) {
        this.consultantRecordRepository = consultantRecordRepository;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.counsellorRepository = counsellorRepository;
        this.messageRepository = messageRepository;
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
            response.setSessionID(record.getSessionID());
            response.setCounsellorID(record.getCounsellorID());
            response.setCounsellorName(allCounsellorNames.get(i));
            response.setTimestamp(allSessionStartTimestamp.get(i));
            response.setDuration(allSessionDuration.get(i));
            response.setUserRating(record.getUserRating());
            response.setAppraisal(record.getAppraisal());
            response.setCounsellorAppraisal(record.getAppraisal());
            response.setRecordID(record.getRecordID());
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
        Long duration = consultantRecordRepository.findHistoricalConsultationDurationByUserID(userID, startTimestamp, endTimestamp);
        if (duration == null) {
            return 0L;
        }
        return duration;
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
        Long duration = consultantRecordRepository.findHistoricalConsultationDurationByCounsellorID(counsellorID, startTimestamp, endTimestamp);
        if (duration == null) {
            return 0L;
        }
        return duration;
    }

    /**
     * add a new consultant record
     * @param counsellorID           The ID of the counsellor
     * @param userID                 The ID of the user
     * @param sessionID              The ID of the session
     * @param userRating             The rating given by the user
     * @param appraisal              The appraisal given by the user
     * @param counsellorAppraisal    The appraisal given by the counsellor
     * @return true if the record is added successfully, false otherwise
     */
    public boolean addConsultantRecord(Long counsellorID, Long userID, Long sessionID, Float userRating, String appraisal, String counsellorAppraisal) {
        // check whether the consultant record exists
        if (consultantRecordRepository.existsByAttributes(counsellorID, userID, sessionID) > 0) {
            return false;
        }

        ConsultantRecord record = new ConsultantRecord();
        record.setCounsellorID(counsellorID);
        record.setUserID(userID);
        record.setSessionID(sessionID);
        record.setUserRating(userRating);
        record.setAppraisal(appraisal);
        record.setCounsellorAppraisal(counsellorAppraisal);

        consultantRecordRepository.save(record);
        return true;
    }

    /**
     * Export the consultant record to a txt file
     * @param recordID      The ID of the consultant record
     * @return the content of the txt file
     */
    public String exportConsultantRecord(Long recordID) {
        StringBuilder content = new StringBuilder();

        // get record info first
        ConsultantRecord record = consultantRecordRepository.findById(recordID).orElse(null);
        if (record == null) {
            return "Error: record not found";
        }
        content.append("recordIO: ").append(record.getRecordID()).append("\t").
                append("counsellorID: ").append(record.getCounsellorID()).append("\t").
                append("userID: ").append(record.getUserID()).append("\t").
                append("sessionID: ").append(record.getSessionID()).append("\t").
                append("userRating: ").append(record.getUserRating()).append("\t").
                append("appraisal: ").append(record.getAppraisal()).append("\t").
                append("counsellorAppraisal: ").append(record.getCounsellorAppraisal()).append("\n");

        // get session info
        Session session = sessionRepository.findSessionBySessionID(record.getSessionID());
        if (session == null) {
            return "Error: session not found";
        }
        content.append("sessionID: ").append(session.getSessionID()).append("\t").
                append("startTimestamp: ").append(session.getStartTimestamp()).append("\t").
                append("endTimestamp: ").append(session.getEndTimestamp()).append("\n");

        // get user info
        String userName = userRepository.findUserNameByUid(record.getUserID());
        String counsellorName = counsellorRepository.findCounsellorNameByCounsellorID(record.getCounsellorID());
        if (userName == null || counsellorName == null) {
            return "Error: user or counsellor not found";
        }

        // get all messages in this session
        List<Message> messages = messageRepository.findMessagesBySessionID(record.getSessionID());
        if (messages == null) {
            return "Error: messages not found";
        }
        for (var message : messages) {
            if (Objects.equals(message.getSenderID(), record.getUserID())) {
                content.append(message.getSendTimestamp()).append("\t").
                        append(userName).append("： ").append(message.getContent()).append("\n");
            } else if (Objects.equals(message.getSenderID(), record.getCounsellorID())) {
                content.append(message.getSendTimestamp()).append("\t").
                        append(counsellorName).append("： ").append(message.getContent()).append("\n");
            }
        }

        return content.toString();
    }
}
