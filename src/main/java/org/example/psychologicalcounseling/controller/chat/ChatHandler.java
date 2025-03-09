package org.example.psychologicalcounseling.controller.chat;

import org.example.psychologicalcounseling.controller.MessageHandler;
import org.example.psychologicalcounseling.param.Request;
import org.example.psychologicalcounseling.param.Response;

import java.util.Map;

public class ChatHandler extends MessageHandler {
    private ChatProxy chat_delegation = new SelfImplementIM();

    public ChatHandler() {
        super();
        requestMap.put("chat", new Request("chat", new String[]{"message"}, this::chat));
    }

    public void setChatDelegation(ChatProxy delegation) {
        chat_delegation = delegation;
    }

    public String transmitMessage(Map<String, ?> request_json) {
        return chat_delegation.transmitMessage(request_json);
    }

    public String pullUnReceivedMessage(Map<String, ?> request_json) {
        return chat_delegation.pullUnReceivedMessage(request_json);
    }

    // 该接口用于测试客户端能否连接到服务器
    // @params: message
    public String chat(Map<String, ?> request_json) {
        String message = (String) request_json.get("message");
        return new Response<String>(200, "success", message).toJsonString();
    }
}
