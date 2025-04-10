package org.example.psychologicalcounseling.repository;

import org.example.psychologicalcounseling.model.Counsellor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CounsellorRepository extends CrudRepository<Counsellor, Long> {

    @Query(nativeQuery = true, value = "SELECT nickname FROM Counsellor WHERE counsellorID=:consultantID")
    String findCounsellorNameByCounsellorID(Long consultantID);
}
