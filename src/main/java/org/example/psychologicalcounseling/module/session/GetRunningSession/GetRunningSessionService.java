package org.example.psychologicalcounseling.module.session.GetRunningSession;

import org.example.psychologicalcounseling.model.Session;
import org.example.psychologicalcounseling.repository.SessionRepository;
import org.example.psychologicalcounseling.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GetRunningSessionService {
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    public GetRunningSessionService(SessionRepository sessionRepository, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    public GetRunningSessionResponse getRunningSession(Long userID) {
        List<Session> sessions = sessionRepository.findRunningSessionByUserID(userID);
        List<String> allUsernames = sessions.stream().map((session)->{
            if (userID.equals(session.getFirstUserID())) {
                return userRepository.findUserNameByUid(session.getSecondUserID());
            } else {
                return userRepository.findUserNameByUid(session.getFirstUserID());
            }
        }).toList();

        // save the session information into the response
        GetRunningSessionResponse.Session[] sessionArray = new GetRunningSessionResponse.Session[sessions.size()];
        for (int i = 0; i < sessions.size(); i++) {
            Session session = sessions.get(i);
            GetRunningSessionResponse.Session sessionInfo = new GetRunningSessionResponse.Session();
            sessionInfo.setSessionID(session.getSessionID());
            sessionInfo.setFirstUserID(session.getFirstUserID());
            sessionInfo.setSecondUserID(session.getSecondUserID());
            sessionInfo.setStartTimestamp(session.getStartTimestamp());
            sessionInfo.other = new GetRunningSessionResponse.Session.Other("https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png", allUsernames.get(i));

            sessionArray[i] = sessionInfo;
        }
        return new GetRunningSessionResponse(sessionArray);
    }

    public GetRunningSessionNumberResponse getRunningSessionNumber(Long userID) {
        return new GetRunningSessionNumberResponse(sessionRepository.findRunningSessionNumberByUserID(userID));
    }

}
