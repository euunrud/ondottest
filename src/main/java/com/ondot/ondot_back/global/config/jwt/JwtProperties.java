package com.ondot.ondot_back.global.config.jwt;

public interface JwtProperties {
    String SECRET = "ondot";
    int EXPIRATION_TIME = 864000000; // 10일 (1/1000초)
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
