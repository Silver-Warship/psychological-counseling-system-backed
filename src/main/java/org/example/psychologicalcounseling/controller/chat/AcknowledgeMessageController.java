package org.example.psychologicalcounseling.controller.chat;

import org.example.psychologicalcounseling.dto.RequestHandler;
import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.dto.chat.AcknowledgeMessageRequest;
import org.example.psychologicalcounseling.dto.chat.AcknowledgeMessageResponse;
import org.example.psychologicalcounseling.service.chat.ChatService;
import org.springframework.stereotype.Controller;

@Controller
public class AcknowledgeMessageController  extends RequestHandler<AcknowledgeMessageRequest, AcknowledgeMessageResponse> {
    private final ChatService chatService;

    public AcknowledgeMessageController(ChatService chatService) {
        super(AcknowledgeMessageRequest.class, AcknowledgeMessageResponse.class);
        this.chatService = chatService;
    }

    @Override
    public Response<AcknowledgeMessageResponse> handleRequest(AcknowledgeMessageRequest request) {
        if (request.getAckTimestamp() <= 0) {
            return new Response<>(400, "timestamp should more than 0", null);
        }

        return chatService.acknowledgeMessage(request);
    }
}