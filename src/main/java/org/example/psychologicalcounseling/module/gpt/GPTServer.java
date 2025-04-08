package org.example.psychologicalcounseling.module.gpt;

import org.example.psychologicalcounseling.dto.Response;

// This interface defines the contract for sending messages to the GPT server.
// all gpt server implementations should implement this interface.
public interface GPTServer {
    Response<ChatWithGPTResponse> sendMessage(ChatWithGPTRequest request);
}
