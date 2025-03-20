package org.example.psychologicalcounseling.service.session;

import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.dto.chat.CreateSessionRequest;
import org.example.psychologicalcounseling.dto.chat.CreateSessionResponse;
import org.example.psychologicalcounseling.model.Session;
import org.example.psychologicalcounseling.repository.SessionRepository;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    static final long SESSION_TIMEOUT = 600000;

    private final SessionRepository sessionRepository;
    private final SessionTimeoutDetect sessionTimeoutDetect;

    public SessionService(SessionRepository sessionRepository, SessionTimeoutDetect sessionManager) {
        this.sessionRepository = sessionRepository;
        this.sessionTimeoutDetect = sessionManager;
    }

    public Response<CreateSessionResponse> createSession(CreateSessionRequest request) {
        // create a new session in the database
        Session session = new Session();
        session.setStartTimestamp(request.getSessionStartTime());
        session.setFirstUserID(request.getFirstUserID());
        session.setSecondUserID(request.getSecondUserID());
        session.setLastMessageTimestamp(System.currentTimeMillis());
        session.setIsClosed(false);
        sessionRepository.save(session);

        // register the session in session manager
        // the session will be removed after 10 minutes if no activity is detected
        sessionTimeoutDetect.registerSession(session.getSID(), SESSION_TIMEOUT);

        return new Response<>(200, "Session created successfully", new CreateSessionResponse(session.getSID()));
    }
}
