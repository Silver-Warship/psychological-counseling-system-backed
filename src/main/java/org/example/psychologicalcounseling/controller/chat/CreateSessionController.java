package org.example.psychologicalcounseling.controller.chat;

import org.example.psychologicalcounseling.constant.ErrorConstant;
import org.example.psychologicalcounseling.dto.RequestHandler;
import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.module.chat.session.createSession.CreateSessionRequest;
import org.example.psychologicalcounseling.module.chat.session.createSession.CreateSessionResponse;
import org.example.psychologicalcounseling.module.chat.session.SessionService;
import org.springframework.stereotype.Controller;

@Controller
public class CreateSessionController extends RequestHandler<CreateSessionRequest, CreateSessionResponse> {
    private final SessionService sessionService;

    public CreateSessionController(SessionService sessionService) {
        super(CreateSessionRequest.class, CreateSessionResponse.class);
        this.sessionService = sessionService;
    }

    @Override
    public Response<CreateSessionResponse> handleRequest(CreateSessionRequest request) {
        if (request.getSessionStartTime() <= 0) {
            return new Response<>(ErrorConstant.illegalTimestamp.code, ErrorConstant.illegalTimestamp.codeMsg, null);
        }

        return sessionService.createSession(request);
    }
}
