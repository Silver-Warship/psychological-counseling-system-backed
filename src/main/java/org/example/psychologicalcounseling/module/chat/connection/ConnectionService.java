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

    /**
     * Retrieves the WebSocket session associated with the given user ID.
     *
     * @param userID The user ID for which to retrieve the session.
     * @return The WebSocket session associated with the user ID, or null if not found.
     */
    public WebSocketSession getConnection(Long userID) {
        return connectionMap.get(userID);
    }

    /**
     * Registers a new connection for the given user ID.
     *
     * @param request The request containing the user ID and session.
     * @param session The WebSocket session to register.
     * @return A response indicating the success or failure of the operation.
     */
    public Response<RegisterConnectionResponse> registerConnection(RegisterConnectionRequest request, WebSocketSession session) {
        connectionMap.put(request.getUserID(), session);
        return new Response<>(ErrorConstant.successRegisterConnection.code, ErrorConstant.successRegisterConnection.codeMsg, null);
    }
}
