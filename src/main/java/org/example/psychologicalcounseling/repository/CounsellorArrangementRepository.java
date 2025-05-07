package org.example.psychologicalcounseling.repository;

import ch.qos.logback.core.testUtil.StringListAppender;
import org.example.psychologicalcounseling.model.Counsellor;
import org.example.psychologicalcounseling.model.CounsellorArrangement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CounsellorArrangementRepository extends JpaRepository<CounsellorArrangement, Long> {
    List<CounsellorArrangement> findAllByCounsellorID(Long counsellorID);

    @Query("SELECT ca FROM CounsellorArrangement ca " +
            "WHERE ca.arrangeID = :arrangeID")
    CounsellorArrangement findArrangementByID(
            @Param("arrangeID") Long arrangeID
    );

    @Query(nativeQuery = true, value = "SELECT count(*) FROM CounsellorArrangement WHERE startTimestamp >= :startTimestamp AND startTimestamp < :endTimestamp")
    Long findCounsellorNumberByTime(Long startTimestamp, Long endTimestamp);

    @Query(nativeQuery = true, value = "SELECT  DISTINCT counsellorID, arrangeID FROM CounsellorArrangement WHERE startTimestamp >= :startTimestamp AND startTimestamp < :endTimestamp")
    List<Object[]> findIDListByTime(Long startTimestamp, Long endTimestamp);

    @Query(nativeQuery = true, value = "SELECT * FROM CounsellorArrangement WHERE counsellorID = :counsellorID AND startTimestamp >= :startTimestamp AND startTimestamp <= :endTimestamp")
    List<CounsellorArrangement> findAllByCounsellorID(Long counsellorID, Long startTimestamp, Long endTimestamp);

    @Query(nativeQuery = true, value = "SELECT * FROM CounsellorArrangement WHERE startTimestamp >= :startTimestamp AND startTimestamp <= :endTimestamp")
    List<CounsellorArrangement> findAllByCounsellorID(Long startTimestamp, Long endTimestamp);

    @Query(nativeQuery = true, value = "SELECT t2.* FROM CounsellorArrangement t1 LEFT JOIN Counsellor t2 on t1.counsellorID = t2.counsellorID" +
            " WHERE t1.startTimestamp <= :timestamp AND t1.endTimestamp >= :timestamp")
    Counsellor[] findOnDutyCounsellor(Long timestamp);
}
