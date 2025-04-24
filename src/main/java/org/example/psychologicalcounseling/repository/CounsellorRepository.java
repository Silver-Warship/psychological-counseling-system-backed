package org.example.psychologicalcounseling.repository;

import org.example.psychologicalcounseling.model.Counsellor;
import org.example.psychologicalcounseling.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CounsellorRepository extends JpaRepository<Counsellor, Long> {

    @Query(nativeQuery = true, value = "SELECT nickname FROM Counsellor WHERE counsellorID=:consultantID")
    String findCounsellorNameByCounsellorID(Long consultantID);

    Counsellor findByEmail(String email);

    Counsellor findByCounsellorID(Long counsellorID);



}
