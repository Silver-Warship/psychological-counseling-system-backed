package org.example.psychologicalcounseling.controller.chat;

import org.example.psychologicalcounseling.constant.ErrorConstant;
import org.example.psychologicalcounseling.dto.RequestHandler;
import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.module.chat.connection.RegisterConnection.RegisterConnectionRequest;
import org.example.psychologicalcounseling.module.chat.connection.RegisterConnection.RegisterConnectionResponse;
import org.example.psychologicalcounseling.module.chat.connection.ConnectionService;
import org.example.psychologicalcounseling.repository.AccountRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketSession;

@Controller
public class RegisterConnectionController extends RequestHandler<RegisterConnectionRequest, RegisterConnectionResponse> {
    private final ConnectionService connectionService;
    private final AccountRepository accountRepository;

    public RegisterConnectionController(ConnectionService connectionService, AccountRepository accountRepository) {
        super(RegisterConnectionRequest.class, RegisterConnectionResponse.class);
        this.connectionService = connectionService;
        this.accountRepository = accountRepository;
    }

    @Override
    public Response<RegisterConnectionResponse> handleRequest(RegisterConnectionRequest request, WebSocketSession session) {
        // check whether the userID is existed
        if (!accountRepository.existsById(request.getUserID())) {
            return new Response<>(ErrorConstant.noThisUser.code, ErrorConstant.noThisUser.codeMsg, null);
        }

        return connectionService.registerConnection(request, session);
    }
}
