package org.example.psychologicalcounseling.controller.chat;

import org.example.psychologicalcounseling.constant.ErrorConstant;
import org.example.psychologicalcounseling.dto.RequestHandler;
import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.module.chat.message.acknowledgeMessage.AcknowledgeMessageRequest;
import org.example.psychologicalcounseling.module.chat.message.acknowledgeMessage.AcknowledgeMessageResponse;
import org.example.psychologicalcounseling.module.chat.message.MessageService;
import org.springframework.stereotype.Controller;

@Controller
public class AcknowledgeMessageController  extends RequestHandler<AcknowledgeMessageRequest, AcknowledgeMessageResponse> {
    private final MessageService messageService;

    public AcknowledgeMessageController(MessageService messageService) {
        super(AcknowledgeMessageRequest.class, AcknowledgeMessageResponse.class);
        this.messageService = messageService;
    }

    @Override
    public Response<AcknowledgeMessageResponse> handleRequest(AcknowledgeMessageRequest request) {
        if (request.getAckTimestamp() <= 0) {
            return new Response<>(ErrorConstant.illegalTimestamp.code, ErrorConstant.illegalTimestamp.codeMsg, null);
        }
        for (Long messageID : request.getMessageIDs()) {
            if (messageID < 0) {
                return new Response<>(ErrorConstant.negativeMessageID.code, ErrorConstant.negativeMessageID.codeMsg, null);
            }
        }

        return messageService.acknowledgeMessage(request);
    }
}