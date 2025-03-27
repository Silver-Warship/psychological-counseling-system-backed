package org.example.psychologicalcounseling.service.chat;

import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.dto.chat.*;
import org.example.psychologicalcounseling.repository.MessageRepository;
import org.example.psychologicalcounseling.model.Message;
import org.example.psychologicalcounseling.service.connection.ConnectionService;
import org.example.psychologicalcounseling.utils.GetBeanUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class SelfImplementIMService implements ChatService {
    private final MessageRepository messageRepository;

    public SelfImplementIMService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    private void sendMessage(Message message) {
        // send to the receiver if the receiver is online
        Long receiverID = message.getReceiverID();
        ConnectionService connectionService = GetBeanUtil.getBean(ConnectionService.class);
        WebSocketSession session = connectionService.getConnection(receiverID);
        if (session == null || !session.isOpen()) {
            return;
        }

        // package the message which will be sent to the receiver
        PullUnReceivedMessageResponse.Message msgToClient = new PullUnReceivedMessageResponse.Message();
        msgToClient.setMessageID(message.getMessageID());
        msgToClient.setContent(message.getContent());
        msgToClient.setContentType(message.getContentType());
        msgToClient.setTimestamp(message.getSendTimestamp());
        Response<PullUnReceivedMessageResponse.Message> serverResponse = new Response<>(
                200, "there are new message from other user", msgToClient
        );

        // convert the message to json string and send it to the receiver
        try{
            session.sendMessage(new TextMessage(serverResponse.toString()));
        } catch (Exception ignored) {

        }
    }

    @Override
    public Response<TransmitMessageResponse> transmitMessage(TransmitMessageRequest request) {
        // save the message to the database
        Message message = new Message();
        message.setSessionID(request.getSessionID());
        message.setSenderID(request.getSenderID());
        message.setReceiverID(request.getReceiverID());
        message.setContent(request.getContent());
        message.setContentType(request.getContentType());
        message.setSendTimestamp(request.getTimestamp());
        message.setStatus(0);
        messageRepository.save(message);

        // send to the receiver if the receiver is online
        sendMessage(message);

        return new Response<>(200, "success", new TransmitMessageResponse(message.getMessageID()));
    }

    @Override
    public Response<PullUnReceivedMessageResponse> pullUnReceivedMessage(PullUnReceivedMessageRequest request) {
        // get the un-received message from the database in chronological order
        List<Message> messages = messageRepository.findAll().stream().filter(message ->
                message.getStatus() == 0 && Objects.equals(message.getReceiverID(), request.getUserID())
                        && Objects.equals(message.getSessionID(), request.getSessionID())).toList();

        // package the message to response
        PullUnReceivedMessageResponse.Message[] messageArray = new PullUnReceivedMessageResponse.Message[messages.size()];
        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            PullUnReceivedMessageResponse.Message m = new PullUnReceivedMessageResponse.Message();
            m.setMessageID(message.getMessageID());
            m.setContent(message.getContent());
            m.setContentType(message.getContentType());
            m.setTimestamp(message.getSendTimestamp());
            messageArray[i] = m;
        }

        return new Response<>(200, "pull message success", new PullUnReceivedMessageResponse(messageArray));
    }

    @Override
    public Response<AcknowledgeMessageResponse> acknowledgeMessage(AcknowledgeMessageRequest request) {
        // update the message status to 1 and set receive timestamp
        List<Message> messages = messageRepository.findAllById(Arrays.asList(request.getMessageIDs()));
        for (Message message : messages) {
            message.setStatus(1);
            message.setReceiveTimestamp(request.getAckTimestamp());
        }
        messageRepository.saveAll(messages);

        return new Response<>(200, "ack success", null);
    }
}
