package org.example.psychologicalcounseling.service.session;

import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.dto.session.CheckSessionAliveRequest;
import org.example.psychologicalcounseling.dto.session.CheckSessionAliveResponse;
import org.example.psychologicalcounseling.dto.session.CreateSessionRequest;
import org.example.psychologicalcounseling.dto.session.CreateSessionResponse;
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
        sessionTimeoutDetect.registerSession(session.getSessionID(), SESSION_TIMEOUT);

        return new Response<>(200, "Session created successfully", new CreateSessionResponse(session.getSessionID()));
    }

    public Response<CheckSessionAliveResponse> checkSessionAlive(CheckSessionAliveRequest request) {
        // check if the session is still active
        Session session = sessionRepository.findById(request.getSessionID()).orElse(null);
        if (session == null) {
            return new Response<>(604, "Session not found", null);
        }

        if (session.getIsClosed()) {
            return new Response<>(200, "Session is not alive", new CheckSessionAliveResponse(false));
        }

        // update the last activity time of the session
        session.setLastMessageTimestamp(System.currentTimeMillis());
        sessionRepository.save(session);

        return new Response<>(200, "Session is alive", new CheckSessionAliveResponse(true));
    }
}
