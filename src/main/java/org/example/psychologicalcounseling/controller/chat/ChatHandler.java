package org.example.psychologicalcounseling.controller.chat;
import org.example.psychologicalcounseling.controller.MessageHandler;
import org.example.psychologicalcounseling.param.Request;
import org.example.psychologicalcounseling.param.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class ChatHandler extends MessageHandler {
    @Autowired
    @Qualifier("selfImplementIM")
    private ChatProxy chatDelegation;

    public ChatHandler() {
        super();
        requestMap.put("chat", new Request("chat", new String[]{"message"}, this::chat));
        requestMap.put("pullUnReceivedMessage", new Request("pullUnReceivedMessage", new String[]{"user_id"}, this::pullUnReceivedMessage));
    }

    public String transmitMessage(Map<String, ?> requestJson) {
        return chatDelegation.transmitMessage(requestJson);
    }

    public String pullUnReceivedMessage(Map<String, ?> requestJson) {
        return chatDelegation.pullUnReceivedMessage(requestJson);
    }

    // 该接口用于测试客户端能否连接到服务器
    // @params: message
    public String chat(Map<String, ?> requestJson) {
        String message = (String) requestJson.get("message");
        return new Response<String>(200, "success", message).toJsonString();
    }
}
