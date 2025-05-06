package org.example.psychologicalcounseling.repository;

import org.example.psychologicalcounseling.model.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupervisorRepository extends JpaRepository<Supervisor, Long> {
    Supervisor findByEmail(String email);

    Supervisor findBySupervisorID(Long supervisorID);

    @Query(nativeQuery = true, value = "SELECT nickname FROM Supervisor WHERE supervisorID = ?1")
    String findSupervisorNameBySupervisorID(Long supervisorID);

    List<Supervisor> findBySupervisorIDOrNicknameContainingIgnoreCase(Long id, String nickname);

}
