package org.example.psychologicalcounseling.module.gpt.kimi;

import org.example.psychologicalcounseling.constant.ErrorConstant;
import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.module.gpt.ChatWithGPTRequest;
import org.example.psychologicalcounseling.module.gpt.ChatWithGPTResponse;
import org.example.psychologicalcounseling.module.gpt.GPTServer;
import org.example.psychologicalcounseling.module.gpt.kimi.apiResponse.KimiMessage;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KimiServer implements GPTServer {
    // in this server, all clients use the same session
    private String defaultSession = null;
    // record the history of messages in each session
    private final Map<Long, List<KimiMessage>> messagePool = new HashMap<>();


    public Response<ChatWithGPTResponse> sendMessage(ChatWithGPTRequest request) {
        // if first message, then create a new session
        // if not, then get the session and continue
        if (this.defaultSession == null) {
            String sessionID = KimiUtil.registerSession();
            if (sessionID != null) {
                this.defaultSession = sessionID;
            } else {
                return new Response<>(ErrorConstant.failCreateGPTSession.code, ErrorConstant.failCreateGPTSession.codeMsg, null);
            }
        }
        List<KimiMessage> historyMessages = this.messagePool.get(request.getSenderID());
        if (historyMessages == null) {
            historyMessages = KimiUtil.getInitialMessages();
        }
        historyMessages.add(new KimiMessage("user", request.getContent()));
        String response = KimiUtil.chat(this.defaultSession, historyMessages);
        // if the response is null, then the server is busy, notify the client
        // else return the response to the client
        if (response == null) {
            return new Response<>(ErrorConstant.GPTBusy.code, ErrorConstant.GPTBusy.codeMsg, null);
        }

        historyMessages.add(new KimiMessage("system", response));
        messagePool.put(request.getSenderID(), historyMessages);
        return new Response<>(ErrorConstant.newMessageFromGPT.code, ErrorConstant.newMessageFromGPT.codeMsg,
                new ChatWithGPTResponse(response, System.currentTimeMillis()));
    }
}
