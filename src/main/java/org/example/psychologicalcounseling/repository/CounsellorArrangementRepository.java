package org.example.psychologicalcounseling.repository;

import org.example.psychologicalcounseling.model.CounsellorArrangement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CounsellorArrangementRepository extends JpaRepository<CounsellorArrangement, Long> {
    List<CounsellorArrangement> findAllByCounsellorID(Long counsellorID);
}
