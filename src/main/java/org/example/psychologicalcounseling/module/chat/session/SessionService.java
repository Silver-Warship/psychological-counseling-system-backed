package org.example.psychologicalcounseling.module.chat.session;

import org.example.psychologicalcounseling.constant.ErrorConstant;
import org.example.psychologicalcounseling.constant.OtherConstant;
import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.module.chat.session.CloseSession.CloseSessionRequest;
import org.example.psychologicalcounseling.module.chat.session.CloseSession.CloseSessionResponse;
import org.example.psychologicalcounseling.module.chat.session.checkSessionAlive.CheckSessionAliveRequest;
import org.example.psychologicalcounseling.module.chat.session.checkSessionAlive.CheckSessionAliveResponse;
import org.example.psychologicalcounseling.module.chat.session.createSession.CreateSessionRequest;
import org.example.psychologicalcounseling.module.chat.session.createSession.CreateSessionResponse;
import org.example.psychologicalcounseling.model.Session;
import org.example.psychologicalcounseling.repository.SessionRepository;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final SessionTimeoutDetect sessionTimeoutDetect;

    public SessionService(SessionRepository sessionRepository, SessionTimeoutDetect sessionManager) {
        this.sessionRepository = sessionRepository;
        this.sessionTimeoutDetect = sessionManager;
    }

    /**
     * Create a new session in the database and register it in the session manager.
     * The session will be removed after 10 minutes if no activity is detected.
     * @param request The request containing the session details.
     * @return A response indicating the success or failure of the operation.
     */
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
        sessionTimeoutDetect.registerSession(session.getSessionID(), OtherConstant.SessionTimeout);

        return new Response<>(ErrorConstant.successCreateSession.code, ErrorConstant.successCreateSession.codeMsg, new CreateSessionResponse(session.getSessionID()));
    }

    /**
     * Check if the session is still active.
     * @param request The request containing the session ID.
     * @return A response indicating whether the session is alive or not.
     */
    public Response<CheckSessionAliveResponse> checkSessionAlive(CheckSessionAliveRequest request) {
        // check if the session is still active
        Session session = sessionRepository.findById(request.getSessionID()).orElse(null);
        if (session == null) {
            return new Response<>(ErrorConstant.sessionNotExist.code, ErrorConstant.sessionNotExist.codeMsg, null);
        }

        if (session.getIsClosed()) {
            return new Response<>(ErrorConstant.sessionExpired.code, ErrorConstant.sessionExpired.codeMsg, new CheckSessionAliveResponse(false));
        }

        // update the last activity time of the session
        session.setLastMessageTimestamp(System.currentTimeMillis());
        sessionRepository.save(session);

        return new Response<>(ErrorConstant.sessionAlive.code, ErrorConstant.sessionAlive.codeMsg, new CheckSessionAliveResponse(true));
    }

    /**
     * Close the session in the database and notify the session manager to remove it.
     * @param request The request containing the session ID.
     * @return A response indicating the success or failure of the operation.
     */
    public Response<CloseSessionResponse> closeSession(CloseSessionRequest request) {
        // close the session in the database
        sessionRepository.updateSessionBySessionID(request.getSessionID(), true);

        // notify the session manager to remove the session
        Session session = sessionRepository.findById(request.getSessionID()).orElse(null);
        if (session != null) {
            sessionTimeoutDetect.notifyUser(session.getFirstUserID(), request.getSessionID());
            sessionTimeoutDetect.notifyUser(session.getSecondUserID(), request.getSessionID());
        }

        return new Response<>(ErrorConstant.successCloseSession.code, ErrorConstant.successCloseSession.codeMsg, new CloseSessionResponse(request.getSessionID()));
    }
}
