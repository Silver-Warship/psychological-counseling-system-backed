package org.example.psychologicalcounseling.controller.chat;

import org.example.psychologicalcounseling.constant.ErrorConstant;
import org.example.psychologicalcounseling.dto.RequestHandler;
import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.module.chat.session.CloseSession.CloseSessionRequest;
import org.example.psychologicalcounseling.module.chat.session.CloseSession.CloseSessionResponse;
import org.example.psychologicalcounseling.module.chat.session.SessionService;
import org.example.psychologicalcounseling.repository.SessionRepository;
import org.springframework.stereotype.Controller;

@Controller
public class CloseSessionController extends RequestHandler<CloseSessionRequest, CloseSessionResponse> {
    private final SessionRepository sessionRepository;
    private final SessionService sessionService;

    public CloseSessionController(SessionRepository sessionRepository, SessionService sessionService) {
        super(CloseSessionRequest.class, CloseSessionResponse.class);
        this.sessionRepository = sessionRepository;
        this.sessionService = sessionService;
    }

    public Response<CloseSessionResponse> handleRequest(CloseSessionRequest request) {
        // check whether the session is existed
        if (!sessionRepository.existsById(request.getSessionID())) {
            return new Response<>(ErrorConstant.noThisSession.code, ErrorConstant.noThisSession.codeMsg, null);
        }

        // check whether the session is already closed
        if (sessionRepository.findById(request.getSessionID()).orElseThrow().getIsClosed()) {
            return new Response<>(ErrorConstant.sessionAlreadyClosed.code, ErrorConstant.sessionAlreadyClosed.codeMsg, null);
        }

        return sessionService.closeSession(request);
    }
}
