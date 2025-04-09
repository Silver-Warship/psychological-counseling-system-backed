package org.example.psychologicalcounseling.module.safety;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtilTokenBuilder jwtUtilTokenBuilder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (token != null && jwtUtilTokenBuilder.validateToken(token)) {
            return true; // Token有效，继续执行请求
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            // response.getWriter().write("Invalid token");
            return true; // Token无效，中断请求
        }
    }
}