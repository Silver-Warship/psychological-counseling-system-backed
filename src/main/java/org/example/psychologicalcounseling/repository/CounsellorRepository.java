package org.example.psychologicalcounseling.repository;

import org.example.psychologicalcounseling.model.Counsellor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CounsellorRepository extends CrudRepository<Counsellor, Long> {

    @Query(nativeQuery = true, value = "SELECT nickname FROM Counsellor WHERE counsellorID in ?1")
    List<String> findAllCounsellorNameByCounsellorID(List<Long> consultantIDs);
}
