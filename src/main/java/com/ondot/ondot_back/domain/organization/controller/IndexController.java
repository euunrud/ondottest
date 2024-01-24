//package com.ondot.ondot_back.domain.organization.controller;
//
//import com.ondot.ondot_back.domain.organization.entity.Organization;
//import com.ondot.ondot_back.global.config.auth.PrincipalDetails;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//@Controller
//public class IndexController { //테스트 용
//    @GetMapping({"", "/"})
//    public String index() {
//        return "index";
//    }
//
//    @GetMapping("/test/login")
//    public @ResponseBody String testLogin(Authentication authentication, @AuthenticationPrincipal PrincipalDetails userDetails) { //DI(의존성 주입)
//        System.out.println("/test/login =========");
//        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
//        System.out.println("authentication" + principalDetails.getOrgaization());
//
//        System.out.println("userDetails:" + userDetails.getOrgaization());
//        return "세션 정보 확인하기";
//    }
//
//    @GetMapping("/test/oauth/login")
//    public @ResponseBody String testOAuthLogin(Authentication authentication,
//                                               @AuthenticationPrincipal OAuth2User oauth) { //DI(의존성 주입)
//        System.out.println("/test/oauth/login =========");
//        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
//        System.out.println("authentication" + oauth2User.getAttributes());
//        System.out.println("oauth2User:" + oauth.getAttributes());
//        return "OAuth세션 정보 확인하기";
//    }
//
//}
