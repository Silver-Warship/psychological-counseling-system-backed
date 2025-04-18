package org.example.psychologicalcounseling.controller;

import org.example.psychologicalcounseling.constant.ErrorConstant;
import org.example.psychologicalcounseling.module.session.GetRunningSession.GetRunningSessionService;
import org.example.psychologicalcounseling.module.session.GetSessionMessage.GetSessionMessageServer;
import org.example.psychologicalcounseling.repository.AccountRepository;
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

    public UserSessionController(GetRunningSessionService getRunningSessionService, GetSessionMessageServer getSessionMessageServer, AccountRepository accountRepository) {
        this.getRunningSessionService = getRunningSessionService;
        this.getSessionMessageServer = getSessionMessageServer;
        this.accountRepository = accountRepository;
    }

    @PostMapping("/api/getRunningSession")
    public ResponseEntity<?> getRunningSession(@RequestParam Long userID) {
        // check if userID is valid
        if (userID == null || userID < 0 || !accountRepository.existsById(userID)) {
            return ResponseEntity.badRequest().body(ErrorConstant.noThisUser.codeMsg);
        }

        return getRunningSessionService.getRunningSession(userID).buildResponse();
    }

    @GetMapping("/api/getRunningSessionNumber")
    public ResponseEntity<?> getRunningSessionNumber(@RequestParam Long userID) {
        // check if userID is valid
        if (userID == null || userID < 0 || !accountRepository.existsById(userID)) {
            return ResponseEntity.badRequest().body(ErrorConstant.noThisUser.codeMsg);
        }

        return getRunningSessionService.getRunningSessionNumber(userID).buildResponse();
    }

    @GetMapping("/api/getSessionMessages")
    public ResponseEntity<?> getSessionMessages(@RequestParam Long sessionID) {
        // check if sessionID is valid
        if (sessionID == null || sessionID < 0 || !accountRepository.existsById(sessionID)) {
            return ResponseEntity.badRequest().body(ErrorConstant.noThisSession.codeMsg);
        }

        return getSessionMessageServer.getSessionMessage(sessionID).buildResponse();
    }
}
