package org.example.psychologicalcounseling.service.session;

import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.dto.session.SessionCloseNotification;
import org.example.psychologicalcounseling.model.Session;
import org.example.psychologicalcounseling.repository.SessionRepository;
import org.example.psychologicalcounseling.service.connection.ConnectionService;
import org.example.psychologicalcounseling.utils.GetBeanUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
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

    private void notifyUser(Long userID, Long sessionID) {
        // notify the user that the session has timed out
        ConnectionService connectionService = GetBeanUtil.getBean(ConnectionService.class);
        WebSocketSession connection = connectionService.getConnection(userID);
        if (connection == null || !connection.isOpen()) {
            return;
        }

        // send a message to the user
        Response<SessionCloseNotification> response = new Response<>(200, "Session closed",
                new SessionCloseNotification(sessionID));
        try {
            connection.sendMessage(new TextMessage(response.toJsonString()));
        } catch (IOException ignored) {

        }
    }

    private void onTimeOutTrigger(long sessionID, long timeout, long timeoutPeriod) {
        long lastActivityTime = getSessionLastActivityTime(sessionID);
        if (lastActivityTime + timeout <= timeoutPeriod) {
            // set the session as inactive
            Session session = sessionRepository.findById(sessionID).orElseThrow();
            session.setIsClosed(true);
            sessionRepository.save(session);

            // notify the user that the session has timed out
            notifyUser(session.getFirstUserID(), sessionID);
            notifyUser(session.getSecondUserID(), sessionID);
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
