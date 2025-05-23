package org.example.psychologicalcounseling.module.chat.session;

import org.example.psychologicalcounseling.constant.ErrorConstant;
import org.example.psychologicalcounseling.constant.OtherConstant;
import org.example.psychologicalcounseling.constant.ServerMessageType;
import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.module.chat.session.sessionCloseNotification.SessionCloseNotification;
import org.example.psychologicalcounseling.model.Session;
import org.example.psychologicalcounseling.repository.SessionRepository;
import org.example.psychologicalcounseling.module.chat.connection.ConnectionService;
import org.example.psychologicalcounseling.utils.GetBeanUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Date;
import java.util.List;
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

        checkSession();
    }

    /**
     * Check all sessions in the database and set them as inactive if they are timed out.
     * This method is called when the server starts.
     */
    private void checkSession() {
        // get all timeout sessions in the database
        List<String> allTimeoutSessions = sessionRepository.findAllTimeoutSession(OtherConstant.SessionTimeout, System.currentTimeMillis());

        // set the session as inactive
        for (String sessionID : allTimeoutSessions) {
            Session session = sessionRepository.findById(Long.parseLong(sessionID)).orElseThrow();
            session.setIsClosed(true);
            if (session.getLastMessageTimestamp() != null) {
                session.setEndTimestamp(session.getLastMessageTimestamp() + OtherConstant.SessionTimeout);
            } else {
                session.setEndTimestamp(session.getStartTimestamp() + OtherConstant.SessionTimeout);
            }
            sessionRepository.save(session);
        }
    }

    /**
     * Notify the user that the session has timed out.
     * @param userID The user ID of the user to be notified.
     * @param sessionID The session ID of the session that has timed out.
     */
    public void notifyUser(Long userID, Long sessionID) {
        // notify the user that the session has timed out
        ConnectionService connectionService = GetBeanUtil.getBean(ConnectionService.class);
        WebSocketSession connection = connectionService.getConnection(userID);
        if (connection == null || !connection.isOpen()) {
            return;
        }

        // send a message to the user
        Response<SessionCloseNotification> response = new Response<>(ServerMessageType.SESSION_CLOSE,
                ErrorConstant.sessionClosed.code, ErrorConstant.sessionClosed.codeMsg, new SessionCloseNotification(sessionID));
        try {
            connection.sendMessage(new TextMessage(response.toJsonString()));
        } catch (IOException ignored) {

        }
    }

    /**
     * Check if the session is timed out and set it as inactive.
     * @param sessionID The session ID of the session to be checked.
     * @param timeout The timeout period.
     * @param timeoutPeriod The timeout period in milliseconds.
     */
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

    /**
     * Get the last activity time of the session.
     * @param sessionID The session ID of the session to be checked.
     * @return The last activity time of the session.
     */
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
