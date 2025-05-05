package org.example.psychologicalcounseling.module.chat.message;

import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.module.chat.message.PullUnReceivedMessage.PullUnReceivedMessageRequest;
import org.example.psychologicalcounseling.module.chat.message.PullUnReceivedMessage.PullUnReceivedMessageResponse;
import org.example.psychologicalcounseling.module.chat.message.TransmitMessage.TransmitMessageRequest;
import org.example.psychologicalcounseling.module.chat.message.TransmitMessage.TransmitMessageResponse;
import org.example.psychologicalcounseling.module.chat.message.acknowledgeMessage.AcknowledgeMessageRequest;
import org.example.psychologicalcounseling.module.chat.message.acknowledgeMessage.AcknowledgeMessageResponse;

public interface MessageService {
    /**
     * 发送消息
     * @param request 发送消息请求
     * @return        发送消息响应
     */
    Response<TransmitMessageResponse> transmitMessage(TransmitMessageRequest request);

    /**
     * 拉取未接收的消息
     * @param request 拉取未接收消息请求
     * @return        拉取未接收消息响应
     */
    Response<PullUnReceivedMessageResponse> pullUnReceivedMessage(PullUnReceivedMessageRequest request);

    /**
     * 确认消息已接收
     * @param request 消息确认请求
     * @return        确认消息响应
     */
    Response<AcknowledgeMessageResponse> acknowledgeMessage(AcknowledgeMessageRequest request);
}
