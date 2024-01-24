package com.ondot.ondot_back.domain.organization.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class GeneralController { //일반 로그인
    @GetMapping("/signin")
    public String showLoginPage() {
        return "signin"; // Thymeleaf 템플릿 파일의 이름
    }
}
