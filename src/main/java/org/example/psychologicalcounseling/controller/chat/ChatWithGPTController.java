package org.example.psychologicalcounseling.controller.chat;

import org.example.psychologicalcounseling.constant.ErrorConstant;
import org.example.psychologicalcounseling.constant.OtherConstant;
import org.example.psychologicalcounseling.dto.RequestHandler;
import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.module.gpt.ChatWithGPTRequest;
import org.example.psychologicalcounseling.module.gpt.ChatWithGPTResponse;
import org.example.psychologicalcounseling.module.gpt.GPTServer;
import org.example.psychologicalcounseling.repository.AccountRepository;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWithGPTController extends RequestHandler<ChatWithGPTRequest, ChatWithGPTResponse> {
    private final GPTServer gptServer;
    private final AccountRepository accountRepository;

    public ChatWithGPTController(GPTServer gptServer, AccountRepository accountRepository) {
        super(ChatWithGPTRequest.class, ChatWithGPTResponse.class);
        this.gptServer = gptServer;
        this.accountRepository = accountRepository;
    }

    @Override
    public Response<ChatWithGPTResponse> handleRequest(ChatWithGPTRequest request) {
        // check if the timestamp is valid
        if (request.getTimestamp() == null || request.getTimestamp() < System.currentTimeMillis() - OtherConstant.RequestTimeout) {
            return new Response<>(ErrorConstant.illegalTimestamp.code, ErrorConstant.illegalTimestamp.codeMsg, null);
        }

        // check if the user exists
        if (!accountRepository.existsById(request.getSenderID())) {
            return new Response<>(ErrorConstant.noThisUser.code, ErrorConstant.noThisUser.codeMsg, null);
        }

        return gptServer.sendMessage(request);
    }
}
