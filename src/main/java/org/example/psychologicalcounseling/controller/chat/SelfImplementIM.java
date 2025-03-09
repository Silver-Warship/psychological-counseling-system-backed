package org.example.psychologicalcounseling.controller.chat;


import org.example.psychologicalcounseling.param.Response;
import org.example.psychologicalcounseling.param.chat.PullUnReceivedMessageRequest;
import org.example.psychologicalcounseling.param.chat.PullUnReceivedMessageResponse;
import org.example.psychologicalcounseling.param.chat.TransmitMessageRequest;
import org.example.psychologicalcounseling.param.chat.TransmitMessageResponse;

import java.util.Map;

public class SelfImplementIM implements ChatProxy {
    public String transmitMessage(Map<String, ?> request_json) {
        return new Response<>(200, "success", "transmit message").toJsonString();
    }

    public String pullUnReceivedMessage(Map<String, ?> request_json) {
        return new Response<>(200, "success", "pull unreceived message").toJsonString();
    }

    @Override
    public Response<TransmitMessageResponse> transmitMessage(TransmitMessageRequest request) {
        return null;
    }

    @Override
    public Response<PullUnReceivedMessageResponse> pullUnReceivedMessage(PullUnReceivedMessageRequest request) {
        return null;
    }
}
