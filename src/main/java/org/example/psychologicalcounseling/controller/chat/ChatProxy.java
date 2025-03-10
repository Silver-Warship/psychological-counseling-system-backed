package org.example.psychologicalcounseling.controller.chat;

import java.util.Map;

public interface ChatProxy {
    // 接收客户端请求并发送消息
    String transmitMessage(Map<String, ?> request_json);

    // 获取某个用户所有未接收的消息
    String pullUnReceivedMessage(Map<String, ?> request_json);

    // 
}
