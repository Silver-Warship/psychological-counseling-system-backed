package org.example.psychologicalcounseling.controller.chat;

import org.example.psychologicalcounseling.constant.ErrorConstant;
import org.example.psychologicalcounseling.constant.OtherConstant;
import org.example.psychologicalcounseling.dto.RequestHandler;
import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.module.chat.session.createSession.CreateSessionRequest;
import org.example.psychologicalcounseling.module.chat.session.createSession.CreateSessionResponse;
import org.example.psychologicalcounseling.module.chat.session.SessionService;
import org.example.psychologicalcounseling.repository.AccountRepository;
import org.springframework.stereotype.Controller;

@Controller
public class CreateSessionController extends RequestHandler<CreateSessionRequest, CreateSessionResponse> {
    private final SessionService sessionService;
    private final AccountRepository accountRepository;


    public CreateSessionController(SessionService sessionService, AccountRepository accountRepository) {
        super(CreateSessionRequest.class, CreateSessionResponse.class);
        this.sessionService = sessionService;
        this.accountRepository = accountRepository;
    }

    @Override
    public Response<CreateSessionResponse> handleRequest(CreateSessionRequest request) {
        // check if the firstUserID and secondUserID are valid
        if (!accountRepository.existsById(request.getFirstUserID())) {
            return new Response<>(ErrorConstant.noThisUser.code, ErrorConstant.noThisUser.codeMsg + request.getFirstUserID(), null);
        }
        if (!accountRepository.existsById(request.getSecondUserID())) {
            return new Response<>(ErrorConstant.noThisUser.code, ErrorConstant.noThisUser.codeMsg + request.getSecondUserID(), null);
        }

        // check if the start time is valid
        if (request.getSessionStartTime() <= System.currentTimeMillis() - OtherConstant.RequestTimeout) {
            return new Response<>(ErrorConstant.illegalTimestamp.code, ErrorConstant.illegalTimestamp.codeMsg, null);
        }

        return sessionService.createSession(request);
    }
}
