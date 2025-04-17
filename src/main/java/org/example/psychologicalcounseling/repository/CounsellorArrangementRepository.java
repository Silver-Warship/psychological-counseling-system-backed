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
            "WHERE ca.counsellorID = :counsellorID AND " +
                  "ca.startTimestamp = :startTimestamp " +
                  "AND ca.endTimestamp = :endTimestamp")
    CounsellorArrangement findArrangementByParams(
            @Param("counsellorID") Long counsellorID,
            @Param("startTimestamp") Long startTimestamp,
            @Param("endTimestamp") Long endTimestamp
    );
}
