package org.example.psychologicalcounseling.controller.chat;

import org.example.psychologicalcounseling.constant.ErrorConstant;
import org.example.psychologicalcounseling.dto.RequestHandler;
import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.module.chat.message.acknowledgeMessage.AcknowledgeMessageRequest;
import org.example.psychologicalcounseling.module.chat.message.acknowledgeMessage.AcknowledgeMessageResponse;
import org.example.psychologicalcounseling.module.chat.message.MessageService;
import org.example.psychologicalcounseling.repository.MessageRepository;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Vector;

@Controller
public class AcknowledgeMessageController  extends RequestHandler<AcknowledgeMessageRequest, AcknowledgeMessageResponse> {
    private final MessageService messageService;
    private final MessageRepository messageRepository;


    public AcknowledgeMessageController(MessageService messageService, MessageRepository messageRepository) {
        super(AcknowledgeMessageRequest.class, AcknowledgeMessageResponse.class);
        this.messageService = messageService;
        this.messageRepository = messageRepository;
    }

    @Override
    public Response<AcknowledgeMessageResponse> handleRequest(AcknowledgeMessageRequest request) {
        // check if the ack timestamp is valid
        if (request.getAckTimestamp() <= 0) {
            return new Response<>(ErrorConstant.illegalTimestamp.code, ErrorConstant.illegalTimestamp.codeMsg, null);
        }

        // check if the messageIDs are valid
        List<Long> unExistMessageIDs = new Vector<>();
        for (Long messageID : request.getMessageIDs()) {
            if (!messageRepository.existsById(messageID)) {
                unExistMessageIDs.add(messageID);
            }

            if (messageID < 0) {
                return new Response<>(ErrorConstant.negativeMessageID.code, ErrorConstant.negativeMessageID.codeMsg, null);
            }
        }
        if (!unExistMessageIDs.isEmpty()) {
            return new Response<>(ErrorConstant.noThisMessage.code, ErrorConstant.noThisMessage.codeMsg + unExistMessageIDs, null);
        }


        return messageService.acknowledgeMessage(request);
    }
}