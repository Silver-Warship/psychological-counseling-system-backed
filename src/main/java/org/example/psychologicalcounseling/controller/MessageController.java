package org.example.psychologicalcounseling.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import org.example.psychologicalcounseling.dto.RequestHandler;
import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.utils.GetBeanUtil;
import org.json.JSONObject;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

public class MessageController implements WebSocketHandler {
    protected HashMap<String, Class<?>> requestMap;

    public MessageController(MessageController... children) {
        requestMap = new HashMap<>(10);

        // add children's requestMap to this requestMap
        for (MessageController child : children) {
            requestMap.putAll(child.requestMap);
        }
    }

    public String handle(String message, WebSocketSession session) {
        // convert message to json
        JSONObject json, content;
        try {
            json = new JSONObject(message);
            content = json.getJSONObject("data");
        } catch (Exception e) {
            return new Response<>(-1, "Invalid JSON", "null").toJsonString();
        }

        // check if request type is valid
        if (!json.has("type")) {
            return new Response<>(-1, "Missing request type", "null").toJsonString();
        }

        if (!json.has("seq")) {
            return new Response<>(-1, "Missing request seq", "null").toJsonString();
        }

        if (!json.has("data")) {
            return new Response<>(-1, "Missing request data", "null").toJsonString();
        }

        String requestType = json.getString("type");
        if (!requestMap.containsKey(requestType)) {
            return new Response<>(-1, "Invalid request type", "null").toJsonString();
        }

        // check if the required params are present
        RequestHandler<?, ?> request = (RequestHandler<?, ?>)GetBeanUtil.getBean(requestMap.get(requestType));
        String[] requiredParams = request.requestParams();
        for (String param : requiredParams) {
            if (!content.has(param)) {
                return new Response<>(-1, "Missing required parameter: " + param, "null").toJsonString();
            }
        }

        // initialize request object
        Type requestParamType = request.getRequestParamClass();

        // handle request
        Response<?> response;
        try {
            response = request.handleRequest(JSON.parseObject(content.toString(), requestParamType), session);
        } catch (JSONException e) {
            return new Response<>(401, "data content error", null).toJsonString();
        } catch (Exception e) {
            return new Response<>(501, "server internal error", null).toJsonString();
        }

        response.setSeq(json.getString("seq"));
        return response.toJsonString();
    }

    public <T> void registerRequest(String type, Class<T> handler) {
        requestMap.put(type, handler);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String response = handle(message.getPayload().toString(), session);
        try {
            session.sendMessage(new TextMessage(response));
        } catch (IOException ignored) {

        }
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
