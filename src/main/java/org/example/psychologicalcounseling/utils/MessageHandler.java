package org.example.psychologicalcounseling.utils;

import com.alibaba.fastjson.JSON;
import org.example.psychologicalcounseling.dto.RequestHandler;
import org.example.psychologicalcounseling.dto.Response;
import org.json.JSONObject;
import org.springframework.web.socket.*;

import java.lang.reflect.Type;
import java.util.HashMap;

public class MessageHandler implements WebSocketHandler {
    protected HashMap<String, RequestHandler<?, ?>> requestMap;

    public MessageHandler(MessageHandler... children) {
        requestMap = new HashMap<>(10);

        // add children's requestMap to this requestMap
        for (MessageHandler child : children) {
            requestMap.putAll(child.requestMap);
        }
    }

    public MessageHandler() {
        requestMap = new HashMap<>(10);
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
        String request_type = json.getString("type");
        if (!requestMap.containsKey(request_type)) {
            return new Response<>(-1, "Invalid request type", "null").toJsonString();
        }

        // check if the required params are present
        RequestHandler<?, ?> request = requestMap.get(request_type);
        String[] requiredParams = request.requestParams();
        for (String param : requiredParams) {
            if (!json.has(param)) {
                return new Response<>(-1, "Missing required parameter: " + param, "null").toJsonString();
            }
        }

        // initialize request object
        Type requestParamType = request.getRequestParamClass();

        // handle request
        return request.handleRequest(JSON.parseObject(message, requestParamType)).toJsonString();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

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

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
