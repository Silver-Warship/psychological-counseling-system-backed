package org.example.psychologicalcounseling.module.chat.message;

import org.example.psychologicalcounseling.constant.ErrorConstant;
import org.example.psychologicalcounseling.constant.ServerMessageType;
import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.model.Session;
import org.example.psychologicalcounseling.module.chat.message.PullUnReceivedMessage.PullUnReceivedMessageRequest;
import org.example.psychologicalcounseling.module.chat.message.PullUnReceivedMessage.PullUnReceivedMessageResponse;
import org.example.psychologicalcounseling.module.chat.message.TransmitMessage.TransmitMessageRequest;
import org.example.psychologicalcounseling.module.chat.message.TransmitMessage.TransmitMessageResponse;
import org.example.psychologicalcounseling.module.chat.message.acknowledgeMessage.AcknowledgeMessageRequest;
import org.example.psychologicalcounseling.module.chat.message.acknowledgeMessage.AcknowledgeMessageResponse;
import org.example.psychologicalcounseling.repository.MessageRepository;
import org.example.psychologicalcounseling.model.Message;
import org.example.psychologicalcounseling.repository.SessionRepository;
import org.example.psychologicalcounseling.module.chat.connection.ConnectionService;
import org.example.psychologicalcounseling.utils.GetBeanUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class SelfImplementIMService implements MessageService {
    private final MessageRepository messageRepository;
    private final SessionRepository sessionRepository;

    public SelfImplementIMService(MessageRepository messageRepository, SessionRepository sessionRepository) {
        this.messageRepository = messageRepository;
        this.sessionRepository = sessionRepository;
    }

    /**
     * Sends a message to the receiver if they are online.
     * @param message The message to be sent.
     */
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
        msgToClient.setSessionID(message.getSessionID());
        Response<PullUnReceivedMessageResponse.Message> serverResponse = new Response<>(
                ServerMessageType.NEW_MESSAGE, ErrorConstant.newMessage.code, ErrorConstant.newMessage.codeMsg, msgToClient
        );

        // convert the message to json string and send it to the receiver
        try{
            session.sendMessage(new TextMessage(serverResponse.toJsonString()));
        } catch (Exception ignored) {

        }
    }

    /**
     * Transmits a message to the receiver and saves it to the database.
     * @param request The request containing the message details.
     * @return A response indicating the success or failure of the operation.
     */
    @Override
    public Response<TransmitMessageResponse> transmitMessage(TransmitMessageRequest request) {
        // check session
        Session session = sessionRepository.findSessionBySessionID(request.getSessionID());
        if (session == null) {
            return new Response<>(ErrorConstant.sessionNotExist.code, ErrorConstant.sessionNotExist.codeMsg, null);
        }

        // save the message to the database
        Message message = new Message(request);
        messageRepository.save(message);

        // send to the receiver if the receiver is online
        sendMessage(message);

        return new Response<>(ErrorConstant.successSendMessage.code, ErrorConstant.successSendMessage.codeMsg, new TransmitMessageResponse(message.getMessageID()));
    }

    /**
     * Retrieves unreceived messages for a specific user.
     * @param request The request containing the user ID and session ID.
     * @return A response containing the unreceived messages.
     */
    @Override
    public Response<PullUnReceivedMessageResponse> pullUnReceivedMessage(PullUnReceivedMessageRequest request) {
        // get the un-received message from the database in chronological order
        List<Message> messages = messageRepository.findAll().stream().filter(message ->
                message.getStatus() == 0 && Objects.equals(message.getReceiverID(), request.getUserID()) // && Objects.equals(message.getSessionID(), request.getSessionID())
        ).toList();

        // package the message to response
        PullUnReceivedMessageResponse.Message[] messageArray = new PullUnReceivedMessageResponse.Message[messages.size()];
        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            PullUnReceivedMessageResponse.Message m = new PullUnReceivedMessageResponse.Message();
            m.setMessageID(message.getMessageID());
            m.setContent(message.getContent());
            m.setContentType(message.getContentType());
            m.setTimestamp(message.getSendTimestamp());
            m.setSessionID(message.getSessionID());
            messageArray[i] = m;
        }

        return new Response<>(ErrorConstant.successPullMessage.code, ErrorConstant.successPullMessage.codeMsg, new PullUnReceivedMessageResponse(messageArray));
    }

    /**
     * Acknowledges the message by updating its status and receive timestamp.
     * @param request The request containing the message IDs and acknowledgment timestamp.
     * @return A response indicating the success or failure of the operation.
     */
    @Override
    public Response<AcknowledgeMessageResponse> acknowledgeMessage(AcknowledgeMessageRequest request) {
        // update the message status to 1 and set receive timestamp
        List<Message> messages = messageRepository.findAllById(Arrays.asList(request.getMessageIDs()));
        for (Message message : messages) {
            message.setStatus(1);
            message.setReceiveTimestamp(request.getAckTimestamp());
        }
        messageRepository.saveAll(messages);

        return new Response<>(ErrorConstant.successAckMessage.code, ErrorConstant.successAckMessage.codeMsg, null);
    }
}
