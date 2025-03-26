package org.example.psychologicalcounseling.controller.chat;

import org.example.psychologicalcounseling.dto.RequestHandler;
import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.dto.chat.CheckSessionAliveRequest;
import org.example.psychologicalcounseling.dto.chat.CheckSessionAliveResponse;
import org.example.psychologicalcounseling.service.session.SessionService;
import org.springframework.stereotype.Controller;

@Controller
public class CheckSessionAlive extends RequestHandler<CheckSessionAliveRequest, CheckSessionAliveResponse>{
    SessionService sessionService;

    public CheckSessionAlive(SessionService sessionService) {
        super(CheckSessionAliveRequest.class, CheckSessionAliveResponse.class);
        this.sessionService = sessionService;
    }

    @Override
    public Response<CheckSessionAliveResponse> handleRequest(CheckSessionAliveRequest request) {
        return sessionService.checkSessionAlive(request);
    }
}
