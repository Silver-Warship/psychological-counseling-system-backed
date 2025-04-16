package org.example.psychologicalcounseling.repository;

import org.example.psychologicalcounseling.model.SupervisorArrangement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SupervisorArrangementRepository extends JpaRepository<SupervisorArrangement, Long> {

    @Query(nativeQuery = true, value = "SELECT count(*) FROM SupervisorArrangement WHERE startTimestamp >= :startTimestamp AND startTimestamp <= :endTimestamp")
    Long findSupervisorNumberByTime(Long startTimestamp, Long endTimestamp);
}
