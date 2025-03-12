package org.example.psychologicalcounseling.controller.chat;

import org.example.psychologicalcounseling.dto.RequestHandler;
import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.dto.chat.PullUnReceivedMessageRequest;
import org.example.psychologicalcounseling.dto.chat.PullUnReceivedMessageResponse;
import org.example.psychologicalcounseling.service.chat.ChatService;
import org.springframework.stereotype.Controller;

@Controller
public class PullUnReceiveMessageController extends RequestHandler<PullUnReceivedMessageRequest, PullUnReceivedMessageResponse> {
    private final ChatService chatService;

    public PullUnReceiveMessageController(ChatService chatService) {
        super(PullUnReceivedMessageRequest.class, PullUnReceivedMessageResponse.class);
        this.chatService = chatService;
    }

    @Override
    public Response<PullUnReceivedMessageResponse> handleRequest(PullUnReceivedMessageRequest request) {
        return chatService.pullUnReceivedMessage(request);
    }
}
