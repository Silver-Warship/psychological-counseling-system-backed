package org.example.psychologicalcounseling.config;


import org.example.psychologicalcounseling.controller.chat.ChatHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 此处注册websocket服务端的处理器并设置websocket的访问地址
        // 下面的例子注册了一个ChatHandler处理器用于处理通信问题，并设置了/chat为访问地址
        registry.addHandler(chatHandler(), "/chat").setAllowedOrigins("*");
    }

    @Bean
    public ChatHandler chatHandler() {
        return new ChatHandler();
    }
}