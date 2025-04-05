package org.example.psychologicalcounseling.controller.chat;

import org.example.psychologicalcounseling.constant.ErrorConstant;
import org.example.psychologicalcounseling.dto.RequestHandler;
import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.module.chat.message.PullUnReceivedMessage.PullUnReceivedMessageRequest;
import org.example.psychologicalcounseling.module.chat.message.PullUnReceivedMessage.PullUnReceivedMessageResponse;
import org.example.psychologicalcounseling.module.chat.message.MessageService;
import org.example.psychologicalcounseling.repository.AccountRepository;
import org.example.psychologicalcounseling.repository.SessionRepository;
import org.example.psychologicalcounseling.repository.UserRepository;
import org.springframework.stereotype.Controller;

@Controller
public class PullUnReceiveMessageController extends RequestHandler<PullUnReceivedMessageRequest, PullUnReceivedMessageResponse> {
    private final MessageService messageService;
    private final AccountRepository accountRepository;
    private final SessionRepository sessionRepository;

    public PullUnReceiveMessageController(MessageService messageService, AccountRepository accountRepository, SessionRepository sessionRepository) {
        super(PullUnReceivedMessageRequest.class, PullUnReceivedMessageResponse.class);
        this.messageService = messageService;
        this.accountRepository = accountRepository;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Response<PullUnReceivedMessageResponse> handleRequest(PullUnReceivedMessageRequest request) {
        // check whether the userID is existed
        if (!accountRepository.existsById(request.getUserID())) {
            return new Response<>(ErrorConstant.noThisUser.code, ErrorConstant.noThisUser.codeMsg, null);
        }

        // check whether the sessionID is existed
        if (sessionRepository.existsById(request.getSessionID())) {
            return new Response<>(ErrorConstant.noThisSession.code, ErrorConstant.noThisSession.codeMsg, null);
        }

        return messageService.pullUnReceivedMessage(request);
    }
}
