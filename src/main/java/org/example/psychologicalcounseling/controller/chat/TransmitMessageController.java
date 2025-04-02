package org.example.psychologicalcounseling.controller.chat;

import org.example.psychologicalcounseling.constant.ErrorConstant;
import org.example.psychologicalcounseling.dto.RequestHandler;
import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.module.chat.message.TransmitMessage.TransmitMessageRequest;
import org.example.psychologicalcounseling.module.chat.message.TransmitMessage.TransmitMessageResponse;
import org.example.psychologicalcounseling.module.chat.message.MessageService;
import org.springframework.stereotype.Controller;

@Controller
public class TransmitMessageController extends RequestHandler<TransmitMessageRequest, TransmitMessageResponse> {
    private final MessageService messageService;

    public TransmitMessageController(MessageService messageService) {
        super(TransmitMessageRequest.class, TransmitMessageResponse.class);
        this.messageService = messageService;
    }

    @Override
    public Response<TransmitMessageResponse> handleRequest(TransmitMessageRequest request) {
        if (request.getContentType() == null) {
            return new Response<>(ErrorConstant.illegalContentType.code, ErrorConstant.illegalContentType.codeMsg, null);
        }
        if (request.getTimestamp() <= 0) {
            return new Response<>(ErrorConstant.illegalTimestamp.code, ErrorConstant.illegalTimestamp.codeMsg, null);
        }

        return messageService.transmitMessage(request);
    }
}
