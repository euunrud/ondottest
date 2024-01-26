package com.ondot.ondot_back.global.config.jwt.handler;

import com.ondot.ondot_back.global.common.ApiResponseStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

import static com.ondot.ondot_back.global.common.ApiResponseStatus.INVALID_AUTH;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        sendErrorResponse(response, INVALID_AUTH);
    }

    private void sendErrorResponse(HttpServletResponse response, ApiResponseStatus status) throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        String jsonResponse = String.format("{\"code\":\"%d\",\"message\":\"%s\"}",
                status.getHttpStatusCode(), status.getMessage());

        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }
}
