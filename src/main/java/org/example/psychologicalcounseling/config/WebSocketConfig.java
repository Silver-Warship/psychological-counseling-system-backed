package org.example.psychologicalcounseling.config;


import org.example.psychologicalcounseling.controller.chat.ChatController;
import org.example.psychologicalcounseling.module.safety.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer, WebMvcConfigurer {

    @Autowired
    private TokenInterceptor tokenInterceptor;

    //先注释掉，因为拦截器会拦截所有请求，在测试的时候很麻烦
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(tokenInterceptor)
//                .addPathPatterns("/**") // 拦截所有请求
//                .excludePathPatterns("/api/users/**"); // 排除user的子路径，比如登录、注册接口
//    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler(), "/chat").setAllowedOrigins("*");
    }

    @Bean
    public ChatController chatHandler() {
        return new ChatController();
    }
}