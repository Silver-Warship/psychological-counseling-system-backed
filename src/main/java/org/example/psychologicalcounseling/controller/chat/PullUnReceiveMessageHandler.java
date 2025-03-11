package org.example.psychologicalcounseling.controller.chat;

import org.example.psychologicalcounseling.dto.RequestHandler;
import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.dto.chat.PullUnReceivedMessageRequest;
import org.example.psychologicalcounseling.dto.chat.PullUnReceivedMessageResponse;
import org.example.psychologicalcounseling.service.chat.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Service
public class PullUnReceiveMessageHandler extends RequestHandler<PullUnReceivedMessageRequest, PullUnReceivedMessageResponse> {
    @Autowired
    private ChatService chatService;

    public PullUnReceiveMessageHandler() {
        super(PullUnReceivedMessageRequest.class, PullUnReceivedMessageResponse.class);
    }

    @Override
    public Response<PullUnReceivedMessageResponse> handleRequest(PullUnReceivedMessageRequest request) {
        return chatService.pullUnReceivedMessage(request);
    }
}
