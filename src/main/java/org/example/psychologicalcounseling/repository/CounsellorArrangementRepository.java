package org.example.psychologicalcounseling.repository;

import org.example.psychologicalcounseling.model.CounsellorArrangement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CounsellorArrangementRepository extends JpaRepository<CounsellorArrangement, Long> {
    List<CounsellorArrangement> findAllByCounsellorID(Long counsellorID);

    @Query("SELECT ca FROM CounsellorArrangement ca " +
            "WHERE ca.arrangeID = :arrangeID")
    CounsellorArrangement findArrangementByID(
            @Param("arrangeID") Long arrangeID
    );

    @Query(nativeQuery = true, value = "SELECT count(*) FROM CounsellorArrangement WHERE startTimestamp >= :startTimestamp AND startTimestamp <= :endTimestamp")
    Long findCounsellorNumberByTime(Long startTimestamp, Long endTimestamp);
}
