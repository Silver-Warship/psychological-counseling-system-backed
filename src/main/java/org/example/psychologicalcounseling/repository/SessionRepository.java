package org.example.psychologicalcounseling.repository;

import org.example.psychologicalcounseling.dto.chat.CreateSessionRequest;
import org.example.psychologicalcounseling.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  SessionRepository extends JpaRepository<Session, Long> {
    public default Boolean createSession(CreateSessionRequest request) {
        // create a new session in the database
        Session session = new Session();
        session.setStartTimestamp(request.getSessionStartTime());
        session.setFirstUserID(request.getFirstUserID());
        session.setSecondUserID(request.getSecondUserID());
        session.setIsClosed(false);
        this.save(session);
        return true;
    }
}
