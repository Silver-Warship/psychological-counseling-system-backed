package org.example.psychologicalcounseling.repository;

import org.example.psychologicalcounseling.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    Session getSessionBySessionID(Long sid);
    List<Session> findByFirstUserIDOrSecondUserID(Long firstUserID, Long secondUserID);

    @Query(nativeQuery = true, value = "SELECT * FROM Session WHERE firstUserID = :uid or secondUserID = :uid AND isClosed = 0")
    List<Session> getRunningSessionByUserID(@Param("uid") Long userID);
}
