package com.ondot.ondot_back.domain.organization.controller;

import com.ondot.ondot_back.domain.organization.dto.request.AuthSignupRequest;
import com.ondot.ondot_back.domain.organization.dto.request.SigninRequest;
import com.ondot.ondot_back.domain.organization.dto.response.SigninResponse;
import com.ondot.ondot_back.global.common.ApiResponse;
import com.ondot.ondot_back.domain.organization.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

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

}
