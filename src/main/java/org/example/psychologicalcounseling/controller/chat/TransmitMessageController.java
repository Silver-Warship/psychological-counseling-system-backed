package org.example.psychologicalcounseling.controller.chat;

import org.example.psychologicalcounseling.constant.ErrorConstant;
import org.example.psychologicalcounseling.constant.OtherConstant;
import org.example.psychologicalcounseling.dto.RequestHandler;
import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.model.Session;
import org.example.psychologicalcounseling.module.chat.message.TransmitMessage.TransmitMessageRequest;
import org.example.psychologicalcounseling.module.chat.message.TransmitMessage.TransmitMessageResponse;
import org.example.psychologicalcounseling.module.chat.message.MessageService;
import org.example.psychologicalcounseling.repository.AccountRepository;
import org.example.psychologicalcounseling.repository.SessionRepository;
import org.springframework.stereotype.Controller;

@Controller
public class TransmitMessageController extends RequestHandler<TransmitMessageRequest, TransmitMessageResponse> {
    private final MessageService messageService;
    private final AccountRepository accountRepository;
    private final SessionRepository sessionRepository;


    public TransmitMessageController(MessageService messageService, AccountRepository accountRepository, SessionRepository sessionRepository) {
        super(TransmitMessageRequest.class, TransmitMessageResponse.class);
        this.messageService = messageService;
        this.accountRepository = accountRepository;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Response<TransmitMessageResponse> handleRequest(TransmitMessageRequest request) {
        // check if the content type is valid
        if (request.getContentType() == null) {
            return new Response<>(ErrorConstant.illegalContentType.code, ErrorConstant.illegalContentType.codeMsg, null);
        }

        // check if the senderID is valid
        if (!accountRepository.existsById(request.getSenderID())) {
            return new Response<>(ErrorConstant.noThisUser.code, ErrorConstant.noThisUser.codeMsg + request.getSenderID(), null);
        }

        // check if the receiverID is valid
        if (!accountRepository.existsById(request.getReceiverID())) {
            return new Response<>(ErrorConstant.noThisUser.code, ErrorConstant.noThisUser.codeMsg + request.getReceiverID(), null);
        }

        // check if the session is valid
        Session session = sessionRepository.findById(request.getSessionID()).orElse(null);
        if (session == null) {
            return new Response<>(ErrorConstant.noThisSession.code, ErrorConstant.noThisSession.codeMsg + request.getSessionID(), null);
        }
        if (!session.getFirstUserID().equals(request.getSenderID()) && !session.getSecondUserID().equals(request.getSenderID())) {
            return new Response<>(ErrorConstant.userNotInSession.code, ErrorConstant.userNotInSession.codeMsg + request.getSenderID(), null);
        }
        if (!session.getFirstUserID().equals(request.getReceiverID()) && !session.getSecondUserID().equals(request.getReceiverID())) {
            return new Response<>(ErrorConstant.userNotInSession.code, ErrorConstant.userNotInSession.codeMsg + request.getSenderID(), null);
        }

        // check if the timestamp is valid
        if (request.getTimestamp() <= System.currentTimeMillis() - OtherConstant.RequestTimeout) {
            return new Response<>(ErrorConstant.illegalTimestamp.code, ErrorConstant.illegalTimestamp.codeMsg, null);
        }

        return messageService.transmitMessage(request);
    }
}
