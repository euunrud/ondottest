package com.ondot.ondot_back.domain.organization.controller;

import com.ondot.ondot_back.domain.organization.dto.AuthSignupRequest;
import com.ondot.ondot_back.domain.organization.dto.SigninRequest;
import com.ondot.ondot_back.domain.organization.dto.SigninResponse;
import com.ondot.ondot_back.domain.organization.entity.Organization;
import com.ondot.ondot_back.global.common.exception.CustomException;
import com.ondot.ondot_back.global.common.ApiResponse;
import com.ondot.ondot_back.global.config.auth.AuthService;
import com.ondot.ondot_back.global.config.auth.PrincipalDetails;
import com.ondot.ondot_back.global.config.auth.UserService;
import com.ondot.ondot_back.global.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.ondot.ondot_back.domain.organization.enums.OrganizationType;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController { //일반 로그인

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final OrganizationType organizationType;
    /**
     * 1.1 일반 회원가입 API
     *
     * @POST /auth/signup
     * @Body SignupRequest
     */
    @PostMapping("/signup")
    public ApiResponse<String> join(@RequestBody AuthSignupRequest authsignupRequest) {
        try {
            String encodedPassword = passwordEncoder.encode(authsignupRequest.getPassword());
            Organization organization = new Organization(authsignupRequest.getOrganizationId(),
                    authsignupRequest.getName(),
                    encodedPassword,// 암호화 저장 authsignupRequest.getPassword()
                    organizationType,
                    authsignupRequest.getProfileUrl(), " ", " ", " ", " ", " ");

            userService.createUser(organization);

            return ApiResponse.onSuccess("회원가입에 성공하였습니다.");

        } catch (CustomException e) {
            HttpStatus errorCode = e.getErrorCode();
            return ApiResponse.onFailure(
                    String.valueOf(errorCode.value()),
                    errorCode.getReasonPhrase()
            );
        }
    }


    /**
     * 1.2 일반 로그인 API
     *
     * @return accessToken
     * @POST /auth/login
     * @Body SigninRequest
     **/
    @PostMapping("/signin")
    public ApiResponse<SigninResponse> signin(@RequestBody SigninRequest signinRequest) {

        //1.organizationId, password 받아서 인증 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(signinRequest.getOrganizationId(), signinRequest.getPassword());

        // 2. 정상인지 로그인 시도 해봄. authenticationManager로 로그인 시도를 하면
        // PrincipalDetailsService가 호출 loadUserByUsername() 함수가 실행된 후 정상이면 authentication이 리턴됨.
        // authentication이 정상 리턴된다는 것은 -> DB에 있는 organizationId password가 일치한다는 것.
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        System.out.println("유저 인증 성공. 일반 로그인을 진행합니다.");

        // 3. PrincipalDetails를 세션에 담고 (권한 관리 위해. 권한 1개뿐이라면 필요없음) => 로그인이 되었다는 뜻
        PrincipalDetails userEntity = (PrincipalDetails) authentication.getPrincipal();
        System.out.println(userEntity);
        Long organization_id = userEntity.getOrgaization().getId();

        String accessToken = jwtTokenProvider.createAccessToken(organization_id);

        return ApiResponse.onSuccess(new SigninResponse(accessToken, ""));
    }


    /**
     * 1.3 자동 로그인 API
     *
     * @return accessToken
     * @POST /auth/signin/auto
     * @body SigninRequest
     **/
    @PostMapping("/signin/auto")
    public ApiResponse<SigninResponse> signinAuto(@RequestBody SigninRequest signinRequest) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(signinRequest.getOrganizationId(), signinRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        System.out.println("유저 인증 성공. 일반 자동 로그인을 진행합니다.");

        PrincipalDetails userEntity = (PrincipalDetails) authentication.getPrincipal();
//        System.out.println(userEntity);
        Long orgaization_id = userEntity.getOrgaization().getId();

        String accessToken = jwtTokenProvider.createAccessToken(orgaization_id);
        String refreshToken = jwtTokenProvider.createRefreshToken(orgaization_id);
        authService.registerRefreshToken(orgaization_id, refreshToken);

        return ApiResponse.onSuccess(new SigninResponse(accessToken, refreshToken));
    }

    /**
     * 1.4 토큰 재발급 api
     *
     * @return accessToken
     * @POST /auth/jwt
     * access token 만료시 재발급
     * @cookie refreshToken
     */
}
