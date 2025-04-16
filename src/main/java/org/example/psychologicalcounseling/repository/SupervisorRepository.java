package org.example.psychologicalcounseling.repository;

import org.example.psychologicalcounseling.model.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupervisorRepository extends JpaRepository<Supervisor, Long> {
    // Custom query methods can be defined here if needed
    // For example, to find a supervisor by their ID:
    // Optional<Supervisor> findById(Long id);
}
