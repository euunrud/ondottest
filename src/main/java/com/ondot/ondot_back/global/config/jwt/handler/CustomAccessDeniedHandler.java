package com.ondot.ondot_back.global.config.jwt.handler;

import com.ondot.ondot_back.global.common.ApiResponseStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

import static com.ondot.ondot_back.global.common.ApiResponseStatus.INVALID_USER_JWT;

@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        setResponse(response, INVALID_USER_JWT);
    }

    private void setResponse(HttpServletResponse response, ApiResponseStatus status) throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        String jsonResponse = String.format("{\"code\":\"%d\",\"message\":\"%s\"}",
                status.getHttpStatusCode(), status.getMessage());

        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }
}
