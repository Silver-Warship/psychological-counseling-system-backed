package org.example.psychologicalcounseling.repository;

import jakarta.transaction.Transactional;
import org.example.psychologicalcounseling.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    Session getSessionBySessionID(Long sid);

    @Query(nativeQuery = true, value = "SELECT * FROM Session WHERE firstUserID = :uid or secondUserID = :uid AND isClosed = 0")
    List<Session> getRunningSessionByUserID(@Param("uid") Long userID);

    @Query(nativeQuery = true, value = "SELECT startTimestamp FROM Session WHERE sessionID in ?1")
    List<Long> getSessionStartTimestampBySessionID(List<Long> sessionID);

    @Query(nativeQuery = true, value = "SELECT endTimestamp - startTimestamp FROM Session WHERE sessionID in ?1")
    List<Long> getSessionDurationBySessionID(List<Long> sessionID);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE Session SET isClosed = :isClosed WHERE sessionID = :sessionID")
    void updateSessionBySessionID(Long sessionID, Boolean isClosed);
}
