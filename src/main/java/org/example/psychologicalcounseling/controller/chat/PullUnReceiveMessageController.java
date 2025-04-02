package org.example.psychologicalcounseling.controller.chat;

import org.example.psychologicalcounseling.dto.RequestHandler;
import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.module.chat.message.PullUnReceivedMessage.PullUnReceivedMessageRequest;
import org.example.psychologicalcounseling.module.chat.message.PullUnReceivedMessage.PullUnReceivedMessageResponse;
import org.example.psychologicalcounseling.module.chat.message.MessageService;
import org.springframework.stereotype.Controller;

@Controller
public class PullUnReceiveMessageController extends RequestHandler<PullUnReceivedMessageRequest, PullUnReceivedMessageResponse> {
    private final MessageService messageService;

    public PullUnReceiveMessageController(MessageService messageService) {
        super(PullUnReceivedMessageRequest.class, PullUnReceivedMessageResponse.class);
        this.messageService = messageService;
    }

    @Override
    public Response<PullUnReceivedMessageResponse> handleRequest(PullUnReceivedMessageRequest request) {
        return messageService.pullUnReceivedMessage(request);
    }
}
