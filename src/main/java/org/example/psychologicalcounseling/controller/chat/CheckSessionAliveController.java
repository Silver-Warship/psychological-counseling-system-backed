package org.example.psychologicalcounseling.controller.chat;

import org.example.psychologicalcounseling.constant.ErrorConstant;
import org.example.psychologicalcounseling.dto.RequestHandler;
import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.module.chat.session.checkSessionAlive.CheckSessionAliveRequest;
import org.example.psychologicalcounseling.module.chat.session.checkSessionAlive.CheckSessionAliveResponse;
import org.example.psychologicalcounseling.module.chat.session.SessionService;
import org.example.psychologicalcounseling.repository.SessionRepository;
import org.springframework.stereotype.Controller;

@Controller
public class CheckSessionAliveController extends RequestHandler<CheckSessionAliveRequest, CheckSessionAliveResponse>{
    private final SessionService sessionService;
    private final SessionRepository sessionRepository;

    public CheckSessionAliveController(SessionService sessionService, SessionRepository sessionRepository) {
        super(CheckSessionAliveRequest.class, CheckSessionAliveResponse.class);
        this.sessionService = sessionService;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Response<CheckSessionAliveResponse> handleRequest(CheckSessionAliveRequest request) {
        // check if session is existed
        if (sessionRepository.existsById(request.getSessionID())) {
            return new Response<>(ErrorConstant.noThisSession.code, ErrorConstant.noThisSession.codeMsg, null);
        }

        return sessionService.checkSessionAlive(request);
    }
}
