package org.example.psychologicalcounseling.module.gpt;

import org.example.psychologicalcounseling.dto.Response;

public interface GPTServer {
    Response<ChatWithGPTResponse> sendMessage(ChatWithGPTRequest request);
}
