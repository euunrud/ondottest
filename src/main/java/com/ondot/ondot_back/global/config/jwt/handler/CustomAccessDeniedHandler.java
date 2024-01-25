package com.ondot.ondot_back.global.config.jwt.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

import static com.ondot.ondot_back.global.common.ApiResponseStatus.INVALID_USER_JWT;

@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler { //인가(Authorization) 오류
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("403 접근 권환이 없습니다.");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write( "{" + "\"isSuccess\":false, "
                + "\"code\":\"" + INVALID_USER_JWT.getCode() + "\","
                + "\"message\":\"" + INVALID_USER_JWT.getMessage() + "\"}");
        response.getWriter().flush();
    }
}
