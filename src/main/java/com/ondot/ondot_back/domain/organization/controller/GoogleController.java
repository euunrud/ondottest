//package com.ondot.ondot_back.domain.organization.controller;
//
//import com.ondot.ondot_back.domain.organization.entity.Organization;
//import com.ondot.ondot_back.domain.organization.repository.OrganizationJpaRepository;
//import com.ondot.ondot_back.global.config.auth.PrincipalDetails;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/api/v1/oauth")
//public class GoogleController {
//    @Autowired
//    private OrganizationJpaRepository organizationJpaRepository;
//
//    //OAuth 로그인을 해도 PrincipalDetails
//    //일반 로그인을 해도 PrincipalDetails
////    @GetMapping("/user")
////    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
////        System.out.println("principalDetails :" + principalDetails.getOrgaization());
////        return  "user";
////    }
////
////    @GetMapping("/all")
////    public List<Organization> getAllOrganizations() {
////        return organizationJpaRepository.findAll();
////    }
//
//}
