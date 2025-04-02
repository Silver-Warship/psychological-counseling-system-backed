package org.example.psychologicalcounseling.module.chat.message;

import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.module.chat.message.PullUnReceivedMessage.PullUnReceivedMessageRequest;
import org.example.psychologicalcounseling.module.chat.message.PullUnReceivedMessage.PullUnReceivedMessageResponse;
import org.example.psychologicalcounseling.module.chat.message.TransmitMessage.TransmitMessageRequest;
import org.example.psychologicalcounseling.module.chat.message.TransmitMessage.TransmitMessageResponse;
import org.example.psychologicalcounseling.module.chat.message.acknowledgeMessage.AcknowledgeMessageRequest;
import org.example.psychologicalcounseling.module.chat.message.acknowledgeMessage.AcknowledgeMessageResponse;

public interface MessageService {
    // 接收客户端请求并发送消息
    Response<TransmitMessageResponse> transmitMessage(TransmitMessageRequest request);

    // 获取某个用户所有未接收的消息
    Response<PullUnReceivedMessageResponse> pullUnReceivedMessage(PullUnReceivedMessageRequest request);

    // 确认消息已接收
    Response<AcknowledgeMessageResponse> acknowledgeMessage(AcknowledgeMessageRequest request);
}
