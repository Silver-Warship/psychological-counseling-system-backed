package org.example.psychologicalcounseling.repository;

import org.example.psychologicalcounseling.model.Counsellor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CounsellorRepository extends JpaRepository<Counsellor, Long> {
    @Query(nativeQuery = true, value = "SELECT nickname FROM Counsellor WHERE counsellorID=:consultantID")
    String findCounsellorNameByCounsellorID(Long consultantID);

    @Query(nativeQuery = true, value = "SELECT * FROM Counsellor WHERE email=:email")
    Counsellor findByEmail(String email);

    Counsellor findByCounsellorID(Long counsellorID);

    List<Counsellor> findByCounsellorIDOrNicknameContainingIgnoreCase(Long id, String nickname);


}
