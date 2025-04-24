package org.example.psychologicalcounseling.repository;

import org.example.psychologicalcounseling.model.HelpRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HelpRecordRepository extends JpaRepository<HelpRecord, Long> {
}
