package org.example.psychologicalcounseling.repository;

import org.example.psychologicalcounseling.model.ConsultantRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultantRecordRepository extends JpaRepository<ConsultantRecord, Long> {
    @Query(nativeQuery = true, value = "select t1.recordID, t1.counsellorID, t1.userID, t1.sessionID, t1.userRating, t1.appraisal, t1.counsellorAppraisal " +
            "from ConsultantRecord t1, Session t2 where t1.userID = ?1 and t1.sessionID = t2.sessionID and t2.startTimestamp >= ?2 and t2.startTimestamp <= ?3")
    List<ConsultantRecord> findByUserIDAndTimestampBetween(Long userID, Long startTimestamp, Long endTimestamp);

    @Query(nativeQuery = true, value = "select t1.recordID, t1.counsellorID, t1.userID, t1.sessionID, t1.userRating, t1.appraisal, t1.counsellorAppraisal " +
            "from ConsultantRecord t1, Session t2 where t1.counsellorID = ?1 and t1.sessionID = t2.sessionID and t2.startTimestamp >= ?2 and t2.startTimestamp <= ?3")
    List<ConsultantRecord>  findByCounsellorIDAndTimestampBetween(Long counsellorID, Long startTimestamp, Long endTimestamp);

    @Query(nativeQuery = true, value = "SELECT count(*) FROM ConsultantRecord t1 inner join Session t2 on t1.sessionID = t2.sessionID" +
            " WHERE t1.userID=:userID AND t2.isClosed=1 AND t2.startTimestamp >= :startTimestamp AND t2.startTimestamp <= :endTimestamp")
    Long findCompletedConsultantNumberByUserIDAndTimestampBetween(Long userID, Long startTimestamp, Long endTimestamp);

    @Query(nativeQuery = true, value = "SELECT count(*) FROM ConsultantRecord t1 inner join Session t2 on t1.sessionID = t2.sessionID" +
            " WHERE t1.counsellorID=:counsellorID AND t2.isClosed=1 AND t2.startTimestamp >= :startTimestamp AND t2.startTimestamp <= :endTimestamp")
    Long findCompletedConsultantNumberByCounsellorIDAndTimestampBetween(Long counsellorID, Long startTimestamp, Long endTimestamp);


    @Query(nativeQuery = true, value = "SELECT SUM(t2.endTimeStamp - t2.startTimestamp) FROM ConsultantRecord t1 inner join Session t2 on t1.sessionID = t2.sessionID " +
            "WHERE t1.userID=:userID AND t2.isClosed=1 AND t2.startTimestamp >= :startTimestamp AND t2.startTimestamp <= :endTimestamp")
    Long findHistoricalConsultationDurationByUserID(Long userID, Long startTimestamp, Long endTimestamp);


    @Query(nativeQuery = true, value = "SELECT SUM(t2.endTimeStamp - t2.startTimestamp) FROM ConsultantRecord t1 inner join Session t2 on t1.sessionID = t2.sessionID " +
            "WHERE t1.counsellorID=:counsellorID AND t2.isClosed=1 AND t2.startTimestamp >= :startTimestamp AND t2.startTimestamp <= :endTimestamp")
    Long findHistoricalConsultationDurationByCounsellorID(Long counsellorID, Long startTimestamp, Long endTimestamp);
}
