package org.example.psychologicalcounseling.controller.chat;

import org.example.psychologicalcounseling.constant.ErrorConstant;
import org.example.psychologicalcounseling.dto.RequestHandler;
import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.module.chat.connection.RegisterConnection.RegisterConnectionRequest;
import org.example.psychologicalcounseling.module.chat.connection.RegisterConnection.RegisterConnectionResponse;
import org.example.psychologicalcounseling.module.chat.connection.ConnectionService;
import org.example.psychologicalcounseling.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketSession;

@Controller
public class RegisterConnectionController extends RequestHandler<RegisterConnectionRequest, RegisterConnectionResponse> {
    private final ConnectionService connectionService;
    private final UserRepository userRepository;

    public RegisterConnectionController(ConnectionService connectionService, UserRepository userRepository) {
        super(RegisterConnectionRequest.class, RegisterConnectionResponse.class);
        this.connectionService = connectionService;
        this.userRepository = userRepository;
    }

    @Override
    public Response<RegisterConnectionResponse> handleRequest(RegisterConnectionRequest request, WebSocketSession session) {
        // check whether the userID is existed
        if (userRepository.findById(request.getUserID()).isEmpty()) {
            return new Response<>(ErrorConstant.noThisUser.code, ErrorConstant.noThisUser.codeMsg, null);
        }

        return connectionService.registerConnection(request, session);
    }
}
