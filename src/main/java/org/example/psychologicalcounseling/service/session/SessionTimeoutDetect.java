package org.example.psychologicalcounseling.service.session;

import org.example.psychologicalcounseling.model.Session;
import org.example.psychologicalcounseling.repository.SessionRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


// this class is responsible for managing the sessions
// for example, if a session times out, it will be removed and set as inactive in the database

@Component
public class SessionTimeoutDetect {
    private final SessionRepository sessionRepository;
    private final Timer timer;

    public SessionTimeoutDetect(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
        this.timer = new Timer();
    }

    private void onTimeOutTrigger(long sessionID, long timeout, long timeoutPeriod) {
        long lastActivityTime = getSessionLastActivityTime(sessionID);
        if (lastActivityTime + timeout <= timeoutPeriod) {
            // set the session as inactive
            Session session = sessionRepository.findById(sessionID).orElseThrow();
            session.setIsClosed(true);
            sessionRepository.save(session);
        } else {
            // add a new timer task to remove the session after the timeout
            addTimerTask(sessionID, timeout);
        }
    }

    private Long getSessionLastActivityTime(long sessionID) {
        // get the last activity time of the session
        return sessionRepository.findById(sessionID).orElseThrow().getLastMessageTimestamp();
    }

    private void addTimerTask(long sessionID, long timeout) {
        // calculate the timeout period
        long timeoutPeriod = getSessionLastActivityTime(sessionID) + timeout;

        // add a timer task to remove the session after the timeout
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                onTimeOutTrigger(sessionID, timeout, timeoutPeriod);
                cancel();
            }
        };

        timer.schedule(task, new Date(timeoutPeriod));
    }

    public void registerSession(long sessionID, long timeout) {
        // add timer task to remove the session after the timeout
        addTimerTask(sessionID, timeout);
    }
}
