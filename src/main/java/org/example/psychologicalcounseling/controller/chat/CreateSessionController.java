package org.example.psychologicalcounseling.controller.chat;

import org.example.psychologicalcounseling.dto.RequestHandler;
import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.dto.chat.CreateSessionRequest;
import org.example.psychologicalcounseling.dto.chat.CreateSessionResponse;
import org.example.psychologicalcounseling.service.session.SessionService;
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
        return sessionService.createSession(request);
    }
}
