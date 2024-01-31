package com.ondot.ondot_back.domain.organization.controller;

import com.ondot.ondot_back.domain.organization.dto.request.AuthSignupRequest;
import com.ondot.ondot_back.domain.organization.dto.request.SigninRequest;
import com.ondot.ondot_back.domain.organization.dto.response.OrganizationGetResponse;
import com.ondot.ondot_back.domain.organization.dto.response.SigninResponse;
import com.ondot.ondot_back.domain.organization.entity.Organization;
import com.ondot.ondot_back.domain.organization.service.OrganizationService;
import com.ondot.ondot_back.global.common.ApiResponse;
import com.ondot.ondot_back.domain.organization.service.AuthService;
import com.ondot.ondot_back.global.config.auth.PrincipalDetails;
import com.ondot.ondot_back.global.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final OrganizationService organizationService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ApiResponse<String> join(@RequestBody AuthSignupRequest authsignupRequest) {
        authService.createUser(authsignupRequest);
        return ApiResponse.onSuccess("회원가입에 성공하였습니다.");
    }

    @PostMapping("/signin")
    public ApiResponse<SigninResponse> signin(@RequestBody SigninRequest signinRequest) {
        SigninResponse response = authService.signin(signinRequest);
        return ApiResponse.onSuccess(response);
    }

    @PostMapping("/signin/auto")
    public ApiResponse<SigninResponse> signinAuto(@RequestBody SigninRequest signinRequest) {
        SigninResponse response = authService.signinAuto(signinRequest);
        return ApiResponse.onSuccess(response);
    }

    @GetMapping("/user")
    public String user(Authentication authentication, @AuthenticationPrincipal PrincipalDetails userDetails) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("principal : "+principal.getOrgaization().getId());
        System.out.println("principal : "+principal.getOrgaization().getName());
        System.out.println("principal : "+principal.getOrgaization().getPassword());

        System.out.println("principal : " + userDetails.getPassword());


        return "user";
    }

    @GetMapping("/mypage")
    public ApiResponse<OrganizationGetResponse> getOrganization(final Principal principal) {
        Organization findOrganization = authService.getOrganization(JwtTokenProvider.getUserIdFromPrincipal(principal));
        return ApiResponse.onSuccess(
                OrganizationGetResponse.from(findOrganization)
        );
    }
//    @PostMapping("/profile")
//    public ApiResponse<String> updateProfile(@AuthenticationPrincipal PrincipalDetails principalDetails,
//                                             @PathVariable String profileUrl) {
//        Organization organization = principalDetails.getOrgaization();
//        authService.updateProfile(organization, profileUrl);
//        return ApiResponse.onSuccess("프로필이 성공적으로 변경되었습니다.");
//    }
//
//    @PostMapping("/pw")
//    public ApiResponse<String> changePassword(@AuthenticationPrincipal PrincipalDetails principalDetails,
//                                              @PathVariable String password) {
//        Organization organization = principalDetails.getOrgaization();
//        authService.updateProfile(organization, password);
//        return ApiResponse.onSuccess("비밀번호가 성공적으로 변경되었습니다.");
//    }


//    @PostMapping("/oauth2/google")
//http://localhost:8080/oauth2/authorization/google 현재
//@GetMapping("/googleLogin")
//public String authTest(final HttpServletRequest request, final HttpServletResponse response) {
//    String redirectURL = "https://accounts.google.com/o/oauth2/v2/auth?client_id=" + googleClientId
//            + "&redirect_uri=" + googleRedirectUrl + "&response_type=code&scope=email profile";
//    try {
//        response.sendRedirect(redirectURL);
//    } catch (Exception e) {
//        log.info("authTest = {}", e);
//    }
//
//    return "SUCCESS";
//}
//
//    // Tip : JWT를 사용하면 UserDetailsService를 호출하지 않기 때문에 @AuthenticationPrincipal 사용 불가능.
//    // 왜냐하면 @AuthenticationPrincipal은 UserDetailsService에서 리턴될 때 만들어지기 때문이다.
//



}
