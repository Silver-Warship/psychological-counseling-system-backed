package org.example.psychologicalcounseling.module.safety;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    private final JwtUtilTokenBuilder jwtUtilTokenBuilder;

    public TokenInterceptor(JwtUtilTokenBuilder jwtUtilTokenBuilder) {
        this.jwtUtilTokenBuilder = jwtUtilTokenBuilder;
    }

    /**
     * 在请求处理之前进行拦截
     * @param request  请求对象
     * @param response 响应对象
     * @param handler  处理器
     * @return true表示继续执行请求，false表示中断请求
     * @throws Exception 异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (token != null && jwtUtilTokenBuilder.validateToken(token)) {
            return true; // Token有效，继续执行请求
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid token");
            return false; // Token无效，中断请求
        }
    }
}