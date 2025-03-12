package org.example.psychologicalcounseling.controller.chat;

import org.example.psychologicalcounseling.dto.RequestHandler;
import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.dto.chat.TransmitMessageRequest;
import org.example.psychologicalcounseling.dto.chat.TransmitMessageResponse;
import org.springframework.stereotype.Controller;

@Controller
public class TransmitMessageController extends RequestHandler<TransmitMessageRequest, TransmitMessageResponse> {
    public TransmitMessageController() {
        super(TransmitMessageRequest.class, TransmitMessageResponse.class);
    }

    @Override
    public Response<TransmitMessageResponse> handleRequest(TransmitMessageRequest request) {
        return null;
    }
}
