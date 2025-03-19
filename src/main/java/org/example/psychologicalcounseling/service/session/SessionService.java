package org.example.psychologicalcounseling.service.session;

import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.dto.chat.CreateSessionRequest;
import org.example.psychologicalcounseling.dto.chat.CreateSessionResponse;
import org.example.psychologicalcounseling.model.Session;
import org.example.psychologicalcounseling.repository.SessionRepository;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Response<CreateSessionResponse> createSession(CreateSessionRequest request) {
        // create a new session in the database
        Session session = new Session();
        session.setStartTimestamp(request.getSessionStartTime());
        session.setFirstUserID(request.getFirstUserID());
        session.setSecondUserID(request.getSecondUserID());
        session.setIsClosed(false);
        sessionRepository.save(session);

        return new Response<>(200, "Session created successfully", new CreateSessionResponse(session.getSID()));
    }
}
