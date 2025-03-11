package org.example.psychologicalcounseling.controller.chat;

import org.example.psychologicalcounseling.dto.RequestHandler;
import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.dto.chat.TransmitMessageRequest;
import org.example.psychologicalcounseling.dto.chat.TransmitMessageResponse;

public class TransmitMessageHandler extends RequestHandler<TransmitMessageRequest, TransmitMessageResponse> {
    public TransmitMessageHandler() {
        super(TransmitMessageRequest.class, TransmitMessageResponse.class);
    }

    @Override
    public Response<TransmitMessageResponse> handleRequest(TransmitMessageRequest request) {
        return null;
    }
}
