package org.example.psychologicalcounseling.controller.chat;

import org.example.psychologicalcounseling.dto.RequestHandler;
import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.module.gpt.ChatWithGPTRequest;
import org.example.psychologicalcounseling.module.gpt.ChatWithGPTResponse;
import org.example.psychologicalcounseling.module.gpt.GPTServer;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWithGPTController extends RequestHandler<ChatWithGPTRequest, ChatWithGPTResponse> {
    private final GPTServer gptServer;

    public ChatWithGPTController(GPTServer gptServer) {
        super(ChatWithGPTRequest.class, ChatWithGPTResponse.class);
        this.gptServer = gptServer;
    }

    @Override
    public Response<ChatWithGPTResponse> handleRequest(ChatWithGPTRequest request) {
        return gptServer.sendMessage(request);
    }
}
