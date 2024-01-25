package com.ondot.ondot_back.global.common;

import lombok.Getter;

@Getter
public enum ApiResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    디(true, 1000, "요청에 성공하였습니다."),

    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    EXPIRED_JWT(false, 2003, "만료된 JWT입니다."),
    INVALID_USER_JWT(false,2004,"권한이 없는 유저의 접근입니다."),
    INVALID_AUTH(false, 2005, "유효하지 않은 회원 정보입니다."),
    DIFFERENT_REFRESH_TOKEN(false, 2006, "유저 정보와 일치하지 않는 refresh token입니다."),

    POST_USERS_EXISTS_ID(false, 2007, "이미 존재하는 아아디입니다."),
    DATABASE_ERROR(false, 2008, "데이터베이스 오류가 발생하였습니다.");

    private final boolean success;
    private final int code;
    private final String message;

    ApiResponseStatus(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
