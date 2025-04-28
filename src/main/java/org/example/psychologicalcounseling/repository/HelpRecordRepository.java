package org.example.psychologicalcounseling.repository;

import org.example.psychologicalcounseling.model.HelpRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HelpRecordRepository extends JpaRepository<HelpRecord, Long> {
    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM HelpRecord WHERE counsellorID = ?1 AND supervisorID = ?2 AND userSessionID = ?3 AND helpSessionID = ?4")
    int existsByAllAttributes(Long counsellorID, Long supervisorID, Long userSessionID, Long helpSessionID);
}
