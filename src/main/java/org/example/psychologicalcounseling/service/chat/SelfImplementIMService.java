package org.example.psychologicalcounseling.service.chat;


import org.example.psychologicalcounseling.dto.Response;
import org.example.psychologicalcounseling.dto.chat.PullUnReceivedMessageRequest;
import org.example.psychologicalcounseling.dto.chat.PullUnReceivedMessageResponse;
import org.example.psychologicalcounseling.dto.chat.TransmitMessageRequest;
import org.example.psychologicalcounseling.dto.chat.TransmitMessageResponse;
import org.springframework.stereotype.Service;

@Service
public class SelfImplementIMService implements ChatService {
    @Override
    public Response<TransmitMessageResponse> transmitMessage(TransmitMessageRequest request) {
        return new Response<>(200, "success", new TransmitMessageResponse("123"));
    }

    @Override
    public Response<PullUnReceivedMessageResponse> pullUnReceivedMessage(PullUnReceivedMessageRequest request) {
        return new Response<>(200, "success", new PullUnReceivedMessageResponse(new String[]{"1", "2"}));
    }
}
