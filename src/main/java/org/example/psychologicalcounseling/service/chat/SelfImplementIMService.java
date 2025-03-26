package org.example.psychologicalcounseling.service.chat;

import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.dto.chat.*;
import org.example.psychologicalcounseling.repository.MessageRepository;
import org.example.psychologicalcounseling.model.Message;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class SelfImplementIMService implements ChatService {
    private final MessageRepository messageRepository;

    public SelfImplementIMService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Response<TransmitMessageResponse> transmitMessage(TransmitMessageRequest request) {
        Message message = new Message();
        message.setSenderID(request.getSenderID());
        message.setReceiverID(request.getReceiverID());
        message.setContent(request.getContent());
        message.setContentType(request.getContentType());
        message.setSendTimestamp(request.getTimestamp());
        message.setStatus(0);
        messageRepository.save(message);

        return new Response<>(200, "success", new TransmitMessageResponse(message.getMID()));
    }

    @Override
    public Response<PullUnReceivedMessageResponse> pullUnReceivedMessage(PullUnReceivedMessageRequest request) {
        // 按时间顺序获取所有未接收的消息
        // 消息状态为0的数据表示未接受说
        List<Message> messages = messageRepository.findAll().stream().filter(message ->
                message.getStatus() == 0 && Objects.equals(message.getReceiverID(), request.getUserID())
                        && Objects.equals(message.getSessionID(), request.getSessionID())).toList();

        // 依次更新消息状态并存回数据库
        PullUnReceivedMessageResponse.Message[] messageArray = new PullUnReceivedMessageResponse.Message[messages.size()];
        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            PullUnReceivedMessageResponse.Message m = new PullUnReceivedMessageResponse.Message();
            m.setMessageID(message.getMID());
            m.setContent(message.getContent());
            m.setContentType(message.getContentType());
            m.setTimestamp(message.getSendTimestamp());
            messageArray[i] = m;
        }

        return new Response<>(200, "pull message success", new PullUnReceivedMessageResponse(messageArray));
    }

    @Override
    public Response<AcknowledgeMessageResponse> acknowledgeMessage(AcknowledgeMessageRequest request) {
        // 更新消息状态为1表示已接收
        List<Message> messages = messageRepository.findAllById(Arrays.asList(request.getMessageIDs()));
        for (Message message : messages) {
            message.setStatus(1);
            message.setReceiveTimestamp(request.getAckTimestamp());
        }
        messageRepository.saveAll(messages);

        return new Response<>(200, "ack success", null);
    }
}
