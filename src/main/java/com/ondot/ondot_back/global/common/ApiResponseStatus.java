package com.ondot.ondot_back.global.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ApiResponseStatus {
    /**
     * 401 UNAUTHROZIED
     */
    INVALID_USER_JWT(HttpStatus.UNAUTHORIZED, "권한이 없는 유저의 접근입니다."),
    INVALID_AUTH(HttpStatus.UNAUTHORIZED, "유효하지 않은 회원 정보입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
