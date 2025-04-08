package org.example.psychologicalcounseling.module.session;

import org.example.psychologicalcounseling.model.Session;
import org.example.psychologicalcounseling.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetRunningSessionService {
    private final SessionRepository sessionRepository;

    public GetRunningSessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public GetRunningSessionResponse getRunningSession(Long userID) {
        List<Session> sessions = sessionRepository.getRunningSessionByUserID(userID);

        // save the session information into the response
        GetRunningSessionResponse.Session[] sessionArray = new GetRunningSessionResponse.Session[sessions.size()];
        for (int i = 0; i < sessions.size(); i++) {
            Session session = sessions.get(i);
            GetRunningSessionResponse.Session sessionInfo = new GetRunningSessionResponse.Session();
            sessionInfo.setSessionID(session.getSessionID());
            sessionInfo.setFirstUserID(session.getFirstUserID());
            sessionInfo.setSecondUserID(session.getSecondUserID());
            sessionArray[i] = sessionInfo;
        }
        return new GetRunningSessionResponse(sessionArray);
    }
}
