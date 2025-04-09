package org.example.psychologicalcounseling.repository;

import org.example.psychologicalcounseling.model.ConsultantRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultantRecordRepository extends JpaRepository<ConsultantRecord, Long> {
    @Query(nativeQuery = true, value = "select t1.recordID, t1.counsellorID, t1.userID, t1.sessionID, t1.userRating, t1.appraisal " +
            "from ConsultantRecord t1, Session t2 where t1.userID = ?1 and t1.sessionID = t2.sessionID and t2.startTimestamp >= ?2 and t2.startTimestamp <= ?3")
    List<ConsultantRecord> findByUserIDAndTimestampBetween(Long userID, Long startTimestamp, Long endTimestamp);

    @Query(nativeQuery = true, value = "select t1.recordID, t1.counsellorID, t1.userID, t1.sessionID, t1.userRating, t1.appraisal " +
            "from ConsultantRecord t1, Session t2 where t1.counsellorID = ?1 and t1.sessionID = t2.sessionID and t2.startTimestamp >= ?2 and t2.startTimestamp <= ?3")
    List<ConsultantRecord>  findByCounsellorIDAndTimestampBetween(Long counsellorID, Long startTimestamp, Long endTimestamp);
}
