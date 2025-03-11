package org.example.psychologicalcounseling.service.chat;

import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.dto.chat.PullUnReceivedMessageRequest;
import org.example.psychologicalcounseling.dto.chat.PullUnReceivedMessageResponse;
import org.example.psychologicalcounseling.dto.chat.TransmitMessageRequest;
import org.example.psychologicalcounseling.dto.chat.TransmitMessageResponse;

public interface ChatService {
    // 接收客户端请求并发送消息
    Response<TransmitMessageResponse> transmitMessage(TransmitMessageRequest request);

    // 获取某个用户所有未接收的消息
    Response<PullUnReceivedMessageResponse> pullUnReceivedMessage(PullUnReceivedMessageRequest request);
}
