package org.example.psychologicalcounseling.service.connection;

import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.dto.chat.RegisterConnectionRequest;
import org.example.psychologicalcounseling.dto.chat.RegisterConnectionResponse;
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
        return new Response<>(200, "Connection registered", null);
    }
}
