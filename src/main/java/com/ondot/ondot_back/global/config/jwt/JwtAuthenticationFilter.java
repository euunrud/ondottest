package com.ondot.ondot_back.global.config.jwt;

import com.ondot.ondot_back.domain.organization.dto.request.SigninRequest;
import com.ondot.ondot_back.global.config.auth.PrincipalDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.io.IOException;
import java.util.ArrayList;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
        setFilterProcessesUrl("/api/v1/auth/signin");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SigninRequest signinRequest = objectMapper.readValue(request.getInputStream(), SigninRequest.class);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(signinRequest.getOrganizationId(), signinRequest.getPassword());

            return authenticationToken;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private Authentication authenticateUser(SigninRequest signinRequest) {
        // 여기에 사용자 인증 로직을 구현
        // 예를 들어, 직접 데이터베이스에서 사용자를 조회하고 비밀번호를 검증하는 등의 작업 수행

        // 아래는 예시로서 인증 성공 시 UserDetails를 생성하여 UsernamePasswordAuthenticationToken을 반환
        UserDetails userDetails = new User("dummyUsername", "dummyPassword", new ArrayList<>());
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        String accessToken = jwtTokenProvider.createAccessToken(principalDetails.getOrgaization().getId());

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + accessToken);
    }
}
