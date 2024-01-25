package com.ondot.ondot_back.global.config.jwt.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

import static com.ondot.ondot_back.global.common.ApiResponseStatus.INVALID_AUTH;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint { //인증(Authentication) 오류
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info("401 인증이 필요합니다.");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{" + "\"isSuccess\":false, "
                + "\"code\":\"" + INVALID_AUTH.getCode() +"\","
                + "\"message\":\"" + INVALID_AUTH.getMessage() + "\"}");

        response.getWriter().flush();
    }
}
