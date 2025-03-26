package org.example.psychologicalcounseling.controller.chat;

import org.example.psychologicalcounseling.dto.RequestHandler;
import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.dto.session.CheckSessionAliveRequest;
import org.example.psychologicalcounseling.dto.session.CheckSessionAliveResponse;
import org.example.psychologicalcounseling.service.session.SessionService;
import org.springframework.stereotype.Controller;

@Controller
public class CheckSessionAliveController extends RequestHandler<CheckSessionAliveRequest, CheckSessionAliveResponse>{
    SessionService sessionService;

    public CheckSessionAliveController(SessionService sessionService) {
        super(CheckSessionAliveRequest.class, CheckSessionAliveResponse.class);
        this.sessionService = sessionService;
    }

    @Override
    public Response<CheckSessionAliveResponse> handleRequest(CheckSessionAliveRequest request) {
        return sessionService.checkSessionAlive(request);
    }
}
