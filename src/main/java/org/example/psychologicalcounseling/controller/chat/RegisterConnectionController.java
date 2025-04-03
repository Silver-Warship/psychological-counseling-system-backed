package org.example.psychologicalcounseling.controller.chat;

import org.example.psychologicalcounseling.dto.RequestHandler;
import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.module.chat.connection.RegisterConnection.RegisterConnectionRequest;
import org.example.psychologicalcounseling.module.chat.connection.RegisterConnection.RegisterConnectionResponse;
import org.example.psychologicalcounseling.module.chat.connection.ConnectionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketSession;

@Controller
public class RegisterConnectionController extends RequestHandler<RegisterConnectionRequest, RegisterConnectionResponse> {
    private final ConnectionService connectionService;

    public RegisterConnectionController(ConnectionService connectionService) {
        super(RegisterConnectionRequest.class, RegisterConnectionResponse.class);
        this.connectionService = connectionService;
    }

    @Override
    public Response<RegisterConnectionResponse> handleRequest(RegisterConnectionRequest request, WebSocketSession session) {
        return connectionService.registerConnection(request, session);
    }
}
