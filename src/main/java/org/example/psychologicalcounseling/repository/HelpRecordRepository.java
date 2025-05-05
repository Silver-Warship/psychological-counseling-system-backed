package org.example.psychologicalcounseling.repository;

import org.example.psychologicalcounseling.model.HelpRecord;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HelpRecordRepository extends JpaRepository<HelpRecord, Long> {
    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM HelpRecord WHERE counsellorID = ?1 AND supervisorID = ?2 AND userSessionID = ?3 AND helpSessionID = ?4")
    int existsByAllAttributes(Long counsellorID, Long supervisorID, Long userSessionID, Long helpSessionID);

    @Query(nativeQuery = true, value = "SELECT DISTINCT t1.* FROM HelpRecord t1 LEFT JOIN Session t2 ON sessionID WHERE t1.counsellorID = ?1 AND t2.startTimestamp >= ?2 AND t2.startTimestamp <= ?3")
    List<HelpRecord> findByCounsellorIDAndStartTimeAndEndTime(Long counsellorID, Long startTimestamp, Long endTimestamp);

    @Query(nativeQuery = true, value = "SELECT DISTINCT t1.* FROM HelpRecord t1 LEFT JOIN Session t2 ON sessionID WHERE t1.supervisorID = ?1 AND t2.startTimestamp >= ?2 AND t2.startTimestamp <= ?3")
    List<HelpRecord> findBySupervisorIDAndStartTimeAndEndTime(Long supervisorID, Long startTimestamp, Long endTimestamp);

    @Query(nativeQuery = true, value = "SELECT endTimestamp - startTimestamp FROM Session WHERE sessionID = ?1")
    Long findHelpSessionDurationByHelpSessionID(Long helpSessionID);

    @Query(nativeQuery = true, value = "SELECT startTimestamp FROM Session WHERE sessionID = ?1")
    Long findHelpSessionStartTimestampByHelpSessionID(Long helpSessionID);
}
