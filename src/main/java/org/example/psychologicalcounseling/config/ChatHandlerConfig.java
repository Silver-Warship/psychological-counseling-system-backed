package org.example.psychologicalcounseling.config;

import org.example.psychologicalcounseling.controller.chat.PullUnReceiveMessageHandler;
import org.example.psychologicalcounseling.utils.MessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatHandlerConfig extends MessageHandler {
    public ChatHandlerConfig() {
        super();
        requestMap.put("pullUnReceivedMessage", pullUnReceiveMessageHandler());
    }

    @Bean
    public PullUnReceiveMessageHandler pullUnReceiveMessageHandler() {
        return new PullUnReceiveMessageHandler();
    }
}
