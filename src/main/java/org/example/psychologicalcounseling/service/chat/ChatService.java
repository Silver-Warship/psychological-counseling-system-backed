package org.example.psychologicalcounseling.service.chat;

import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.dto.chat.*;

public interface ChatService {
    // 接收客户端请求并发送消息
    Response<TransmitMessageResponse> transmitMessage(TransmitMessageRequest request);

    // 获取某个用户所有未接收的消息
    Response<PullUnReceivedMessageResponse> pullUnReceivedMessage(PullUnReceivedMessageRequest request);

    // 确认消息已接收
    Response<AcknowledgeMessageResponse> acknowledgeMessage(AcknowledgeMessageRequest request);
}
