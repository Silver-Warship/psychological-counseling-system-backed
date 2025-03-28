package org.example.psychologicalcounseling.controller.chat;

import org.example.psychologicalcounseling.dto.RequestHandler;
import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.dto.chat.TransmitMessageRequest;
import org.example.psychologicalcounseling.dto.chat.TransmitMessageResponse;
import org.example.psychologicalcounseling.service.chat.ChatService;
import org.springframework.stereotype.Controller;

@Controller
public class TransmitMessageController extends RequestHandler<TransmitMessageRequest, TransmitMessageResponse> {
    private final ChatService chatService;

    public TransmitMessageController(ChatService chatService) {
        super(TransmitMessageRequest.class, TransmitMessageResponse.class);
        this.chatService = chatService;
    }

    @Override
    public Response<TransmitMessageResponse> handleRequest(TransmitMessageRequest request) {
        if (request.getContentType() == null) {
            return new Response<>(400, "error content type", null);
        }
        if (request.getTimestamp() <= 0) {
            return new Response<>(400, "error timestamp", null);
        }

        return chatService.transmitMessage(request);
    }
}
