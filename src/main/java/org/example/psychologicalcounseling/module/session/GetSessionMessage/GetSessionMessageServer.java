package org.example.psychologicalcounseling.module.session.GetSessionMessage;

import org.example.psychologicalcounseling.model.Message;
import org.example.psychologicalcounseling.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetSessionMessageServer {
    private final MessageRepository messageRepository;

    public GetSessionMessageServer(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /*
     * @param sessionID
     * @return all messages in the session
     */
    public GetSessionMessageResponse getSessionMessage(Long sessionID) {
        List<Message> messages = messageRepository.getMessagesBySessionID(sessionID);
        GetSessionMessageResponse response = new GetSessionMessageResponse();
        GetSessionMessageResponse.Message[] tmpMessages = new GetSessionMessageResponse.Message[messages.size()];
        for (int i = 0; i < messages.size(); i++) {
            GetSessionMessageResponse.Message message = new GetSessionMessageResponse.Message();
            message.setContent(messages.get(i).getContent());
            message.setContentType(messages.get(i).getContentType().toString());
            message.setSendID(messages.get(i).getSenderID());
            message.setReceiveID(messages.get(i).getReceiverID());
            message.setTimestamp(messages.get(i).getSendTimestamp());

            tmpMessages[i] = message;
        }

        response.setMessages(tmpMessages);
        return  response;
    }
}
