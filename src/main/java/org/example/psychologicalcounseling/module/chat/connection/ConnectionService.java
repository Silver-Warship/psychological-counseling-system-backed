package org.example.psychologicalcounseling.module.chat.connection;

import org.example.psychologicalcounseling.constant.ErrorConstant;
import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.module.chat.connection.RegisterConnection.RegisterConnectionRequest;
import org.example.psychologicalcounseling.module.chat.connection.RegisterConnection.RegisterConnectionResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;

@Service
public class ConnectionService {
    private final HashMap<Long, WebSocketSession> connectionMap;

    public ConnectionService() {
        this.connectionMap = new HashMap<>();
    }

    public WebSocketSession getConnection(Long userID) {
        return connectionMap.get(userID);
    }

    public Response<RegisterConnectionResponse> registerConnection(RegisterConnectionRequest request, WebSocketSession session) {
        connectionMap.put(request.getUserID(), session);
        return new Response<>(ErrorConstant.successRegisterConnection.code, ErrorConstant.successRegisterConnection.codeMsg, null);
    }
}
