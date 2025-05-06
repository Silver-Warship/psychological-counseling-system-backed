package org.example.psychologicalcounseling.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import org.example.psychologicalcounseling.constant.ErrorConstant;
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
    private Long chunkSize = 0L;
    private StringBuilder bigMessage = new StringBuilder();

    public MessageController(MessageController... children) {
        requestMap = new HashMap<>(10);

        // add children's requestMap to this requestMap
        for (MessageController child : children) {
            requestMap.putAll(child.requestMap);
        }
    }

    /**
     * Handle the incoming message and return a response.
     *
     * @param message the incoming message
     * @param session the WebSocket session
     * @return the response as a JSON string
     */
    public String handle(String message, WebSocketSession session) {
        // check whether message is digit
        try {
            chunkSize = Long.parseLong(message);
            return null;
        } catch (Exception ignored) {

        }

        if (chunkSize > 0) {
            bigMessage.append(message);
            if (bigMessage.length() >= chunkSize) {
                message = bigMessage.toString();
                bigMessage = new StringBuilder();
                chunkSize = 0L;
            } else {
                return null;
            }
        }

        // convert message to json
        JSONObject json, content;
        try {
            json = new JSONObject(message);
            content = json.getJSONObject("data");
        } catch (Exception e) {
            return new Response<>(ErrorConstant.invalidJson.code, ErrorConstant.invalidJson.codeMsg, null).toJsonString();
        }

        // check if request type is valid
        if (!json.has("type")) {
            return new Response<>(ErrorConstant.missRequestType.code, ErrorConstant.missRequestType.codeMsg, null).toJsonString();
        }

        if (!json.has("seq")) {
            return new Response<>(ErrorConstant.missRequestSeq.code, ErrorConstant.missRequestType.codeMsg, null).toJsonString();
        }

        if (!json.has("data")) {
            return new Response<>(ErrorConstant.missRequestData.code, ErrorConstant.missRequestData.codeMsg, null).toJsonString();
        }

        String requestType = json.getString("type");
        if (!requestMap.containsKey(requestType)) {
            return new Response<>(ErrorConstant.invalidRequestType.code, ErrorConstant.invalidRequestType.codeMsg, null).toJsonString();
        }

        // check if the required params are present
        RequestHandler<?, ?> request = (RequestHandler<?, ?>)GetBeanUtil.getBean(requestMap.get(requestType));
        String[] requiredParams = request.requestParams();
        for (String param : requiredParams) {
            if (!content.has(param)) {
                return new Response<>(ErrorConstant.missParameters.code, ErrorConstant.missParameters.codeMsg + param, null).toJsonString();
            }
        }

        // initialize request object
        Type requestParamType = request.getRequestParamClass();

        // handle request
        Response<?> response;
        try {
            response = request.handleRequest(JSON.parseObject(content.toString(), requestParamType), session);
        } catch (JSONException e) {
            return new Response<>(ErrorConstant.dataContentError.code, ErrorConstant.dataContentError.codeMsg, null).toJsonString();
        } catch (Exception e) {
            return new Response<>(ErrorConstant.serverInternalError.code, ErrorConstant.serverInternalError.codeMsg, null).toJsonString();
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
            if (response != null) {
                session.sendMessage(new TextMessage(response));
            }
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
