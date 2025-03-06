package org.example.psychologicalcounseling.controller.chat;


import org.example.psychologicalcounseling.param.Response;
import org.example.psychologicalcounseling.server.chat.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Component
public class SelfImplementIM implements ChatProxy {
    @Autowired
    private MessageService messageService;

    public String transmitMessage(Map<String, ?> requestJson) {
        return new Response<>(200, "success", "transmit message").toJsonString();
    }

    @RequestMapping
    public String pullUnReceivedMessage(Map<String, ?> requestJson) {
        List<String> messages = messageService.getAllMessages();
        return new Response<>(200, "success", messages).toJsonString();
    }
}
