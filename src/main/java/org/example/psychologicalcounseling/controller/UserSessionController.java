package org.example.psychologicalcounseling.controller;

import org.example.psychologicalcounseling.constant.ErrorConstant;
import org.example.psychologicalcounseling.module.session.GetRunningSession.GetRunningSessionService;
import org.example.psychologicalcounseling.module.session.GetSessionMessage.GetSessionMessageServer;
import org.example.psychologicalcounseling.repository.AccountRepository;
import org.example.psychologicalcounseling.repository.SessionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserSessionController {
    private final GetRunningSessionService getRunningSessionService;
    private final GetSessionMessageServer getSessionMessageServer;
    private final AccountRepository accountRepository;
    private final SessionRepository sessionRepository;

    public UserSessionController(GetRunningSessionService getRunningSessionService, GetSessionMessageServer getSessionMessageServer,
                                 AccountRepository accountRepository, SessionRepository sessionRepository) {
        this.getRunningSessionService = getRunningSessionService;
        this.getSessionMessageServer = getSessionMessageServer;
        this.accountRepository = accountRepository;
        this.sessionRepository = sessionRepository;
    }

    /**
     * Get the running session of a specific user
     * @param userID the ID of the user
     * @return the running session of the user
     */
    @PostMapping("/api/getRunningSession")
    public ResponseEntity<?> getRunningSession(@RequestParam Long userID) {
        // check if userID is valid
        if (userID == null || userID < 0 || !accountRepository.existsById(userID)) {
            return ResponseEntity.badRequest().body(ErrorConstant.noThisUser.codeMsg);
        }

        return getRunningSessionService.getRunningSession(userID).buildResponse();
    }

    /**
     * Get the number of running sessions of a specific user
     * @param userID the ID of the user
     * @return the number of running sessions of the user
     */
    @GetMapping("/api/getRunningSessionNumber")
    public ResponseEntity<?> getRunningSessionNumber(@RequestParam Long userID) {
        // check if userID is valid
        if (userID == null || userID < 0 || !accountRepository.existsById(userID)) {
            return ResponseEntity.badRequest().body(ErrorConstant.noThisUser.codeMsg);
        }

        return getRunningSessionService.getRunningSessionNumber(userID).buildResponse();
    }

    /**
     * Get the messages of a specific session
     * @param sessionID the ID of the session
     * @return the messages of the session
     */
    @GetMapping("/api/getSessionMessages")
    public ResponseEntity<?> getSessionMessages(@RequestParam Long sessionID) {
        // check if sessionID is valid
        if (sessionID == null || sessionID < 0 || !sessionRepository.existsById(sessionID)) {
            return ResponseEntity.badRequest().body(ErrorConstant.noThisSession.codeMsg);
        }

        return getSessionMessageServer.getSessionMessage(sessionID).buildResponse();
    }
}
