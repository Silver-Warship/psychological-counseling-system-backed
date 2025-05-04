package org.example.psychologicalcounseling.repository;

import org.example.psychologicalcounseling.model.Counsellor;
import org.example.psychologicalcounseling.model.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SupervisorRepository extends JpaRepository<Supervisor, Long> {
    Supervisor findByEmail(String email);

    Supervisor findBySupervisorID(Long supervisorID);

    @Query(nativeQuery = true, value = "SELECT nickname FROM Supervisor WHERE supervisorID = ?1")
    String findSupervisorNameBySupervisorID(Long supervisorID);
    // Custom query methods can be defined here if needed
    // For example, to find a supervisor by their ID:
    // Optional<Supervisor> findById(Long id);
}
