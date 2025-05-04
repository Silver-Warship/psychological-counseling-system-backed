package org.example.psychologicalcounseling.repository;

import org.example.psychologicalcounseling.model.SupervisorArrangement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupervisorArrangementRepository extends JpaRepository<SupervisorArrangement, Long> {
    @Query(nativeQuery = true, value = "SELECT count(*) FROM SupervisorArrangement WHERE startTimestamp >= :startTimestamp AND startTimestamp <= :endTimestamp")
    Long findSupervisorNumberByTime(Long startTimestamp, Long endTimestamp);

    @Query(nativeQuery = true, value = "SELECT * FROM SupervisorArrangement WHERE supervisorID = :supervisorID AND startTimestamp >= :startTimestamp AND startTimestamp <= :endTimestamp")
    List<SupervisorArrangement> findBySupervisorIDAndStartTimestampBetween(Long supervisorID, Long startTimestamp, Long endTimestamp);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM SupervisorArrangement WHERE supervisorID = :supervisorID AND startTimestamp >= :startTimestamp AND startTimestamp <= :endTimestamp")
    Long countBySupervisorIDAndStartTimestampBetween(Long supervisorID, Long startTimestamp, Long endTimestamp);

    @Query(nativeQuery = true, value = "SELECT  DISTINCT supervisorID, arrangeID FROM SupervisorArrangement WHERE startTimestamp >= :startTimestamp AND startTimestamp <= :endTimestamp")
    List<Object[]> findSupervisorListByTime(Long startTimestamp, Long endTimestamp);

    @Query(nativeQuery = true, value = "SELECT * FROM SupervisorArrangement WHERE startTimestamp >= :startTimestamp AND startTimestamp <= :endTimestamp")
    List<SupervisorArrangement> findByStartTimestampBetween(Long startTimestamp, Long endTimestamp);
}
