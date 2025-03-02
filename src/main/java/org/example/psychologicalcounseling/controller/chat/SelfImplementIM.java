package org.example.psychologicalcounseling.controller.chat;


import org.example.psychologicalcounseling.param.Response;

import java.util.Map;

public class SelfImplementIM implements ChatProxy {
    public String transmitMessage(Map<String, ?> request_json) {
        return new Response<String>(200, "success", "transmit message").toJsonString();
    }

    public String pullUnReceivedMessage(Map<String, ?> request_json) {
        return new Response<String>(200, "success", "pull unreceived message").toJsonString();
    }
}
