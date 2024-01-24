package com.ondot.ondot_back.domain.test.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {
    @GetMapping("/")
    public String basic(){
        return "기본 화면";
    }

    @GetMapping("/test")
    public String Test(){
        return "테스트 화면";
    }
}