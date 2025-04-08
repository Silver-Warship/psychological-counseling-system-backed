package org.example.psychologicalcounseling.controller;

import org.example.psychologicalcounseling.constant.ErrorConstant;
import org.example.psychologicalcounseling.module.session.GetRunningSessionService;
import org.example.psychologicalcounseling.repository.AccountRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserSessionController {
    private final GetRunningSessionService getRunningSessionService;
    private final AccountRepository accountRepository;

    public UserSessionController(GetRunningSessionService getRunningSessionService, AccountRepository accountRepository) {
        this.getRunningSessionService = getRunningSessionService;
        this.accountRepository = accountRepository;
    }

    @PostMapping("getRunningSession")
    public ResponseEntity<?> getRunningSession(@RequestParam Long userID) {
        // check if userID is valid
        if (!accountRepository.existsById(userID)) {
            return ResponseEntity.badRequest().body(ErrorConstant.noThisUser.codeMsg);
        }

        return getRunningSessionService.getRunningSession(userID).buildResponse();
     }
}
