package org.example.psychologicalcounseling.controller;

import org.example.psychologicalcounseling.param.Request;
import org.example.psychologicalcounseling.param.Response;
import org.json.JSONObject;
import org.springframework.web.socket.*;

import java.util.HashMap;
import java.util.Map;

public class MessageHandler implements WebSocketHandler {
    protected HashMap<String, Request> requestMap;

    public MessageHandler(MessageHandler... children) {
        requestMap = new HashMap<>(10);
        requestMap.put("heart_beat", new Request("heart_beat", new String[]{}, this::heartBeat));

        // add children's requestMap to this requestMap
        for (MessageHandler child : children) {
            requestMap.putAll(child.requestMap);
        }
    }

    public MessageHandler() {
        requestMap = new HashMap<>(10);
        requestMap.put("heart_beat", new Request("heart_beat", new String[]{}, this::heartBeat));
    }

    public String handle(String message) {
        // convert message to json
        JSONObject json;
        try {
            json = new JSONObject(message);
        } catch (Exception e) {
            return new Response<>(-1, "Invalid JSON", "null").toJsonString();
        }

        // check if request type is valid
        String requestType = json.getString("type");
        if (!requestMap.containsKey(requestType)) {
            return new Response<>(-1, "Invalid request type", "null").toJsonString();
        }

        // check if the required params are present
        Request request = requestMap.get(requestType);
        String[] requiredParams = request.getRequireParams();
        for (String param : requiredParams) {
            if (!json.has(param)) {
                return new Response<>(-1, "Missing required parameter: " + param, "null").toJsonString();
            }
        }

        // handle request
        return request.handle(json.toMap());
    }

    public String heartBeat(Map<String, ?> requestJson) {
        return new Response<>(200, "success", "活着").toJsonString();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        session.sendMessage(new TextMessage(new Response<>(200, "success", "连接成功").toJsonString()));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String response = handle(message.getPayload().toString());
        session.sendMessage(new TextMessage(response));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        session.sendMessage(new TextMessage(new Response<>(200, "success", "连接关闭").toJsonString()));
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
