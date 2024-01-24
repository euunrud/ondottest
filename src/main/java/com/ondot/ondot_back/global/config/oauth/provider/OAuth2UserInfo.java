package com.ondot.ondot_back.global.config.oauth.provider;

// OAuth2.0 제공자들 마다 응답해주는 속성값 공통
public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();
}

