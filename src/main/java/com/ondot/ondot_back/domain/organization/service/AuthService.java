package com.ondot.ondot_back.domain.organization.service;


import com.ondot.ondot_back.domain.organization.dto.request.AuthSignupRequest;
import com.ondot.ondot_back.domain.organization.dto.request.SigninRequest;
import com.ondot.ondot_back.domain.organization.dto.response.OrganizationGetResponse;
import com.ondot.ondot_back.domain.organization.dto.response.SigninResponse;
import com.ondot.ondot_back.domain.organization.entity.Organization;
import com.ondot.ondot_back.domain.organization.enums.OrganizationType;
import com.ondot.ondot_back.domain.organization.repository.OrganizationJpaRepository;
import com.ondot.ondot_back.domain.organization.repository.OrganizationRepository;
import com.ondot.ondot_back.global.common.exception.CustomException;
import com.ondot.ondot_back.global.config.auth.PrincipalDetails;
import com.ondot.ondot_back.global.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

//@Transactional(readOnly = true)
@Slf4j
@Service
@Lazy
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final OrganizationJpaRepository organizationJpaRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final OrganizationType organizationType;

    private final OrganizationRepository organizationRepository;

    @Autowired
    public AuthService(
            PasswordEncoder passwordEncoder,
            OrganizationJpaRepository organizationJpaRepository,
            JwtTokenProvider jwtTokenProvider,
            OrganizationType organizationType,
            OrganizationRepository organizationRepository
    ) {
        this.passwordEncoder = passwordEncoder;
        this.organizationJpaRepository = organizationJpaRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.organizationType = organizationType;
        this.organizationRepository = organizationRepository;
    }

    @Transactional
    public Organization createUser(AuthSignupRequest authsignupRequest) {
        String organizationId = authsignupRequest.getOrganizationId();

        if (organizationJpaRepository.existsByOrganizationId(organizationId)) {
            throw new CustomException(HttpStatus.NOT_FOUND);
        }
        String encodedPassword = passwordEncoder.encode(authsignupRequest.getPassword());
        Organization organization = new Organization(authsignupRequest.getOrganizationId(),
                authsignupRequest.getName(),
                encodedPassword,
                organizationType,
                authsignupRequest.getProfileUrl());
        organizationJpaRepository.save(organization);

        return organization;
    }

    public SigninResponse signin(SigninRequest signinRequest) {
        // 사용자 인증을 수행하지 않고 직접 토큰 생성
        Long organizationId = findOrganizationIdByUsername(signinRequest.getOrganizationId());
        String accessToken = jwtTokenProvider.createAccessToken(organizationId);

        return new SigninResponse(accessToken, " ");
    }

    public SigninResponse signinAuto(SigninRequest signinRequest) {
        // 사용자 인증을 수행하지 않고 직접 토큰 생성
        Long organizationId = findOrganizationIdByUsername(signinRequest.getOrganizationId());
        String accessToken = jwtTokenProvider.createAccessToken(organizationId);
        String refreshToken = jwtTokenProvider.createRefreshToken(organizationId);
        registerRefreshToken(organizationId, refreshToken);

        return new SigninResponse(accessToken, refreshToken);
    }

    public Long registerRefreshToken(Long organizationid, String refreshToken) {
        Organization organization = organizationJpaRepository.findById(organizationid).orElse(null);

        if (organization == null) {
            organization.setId(organizationid);
        } else {
            organization.setRefreshToken(refreshToken);
            organizationJpaRepository.save(organization);
        }
        return organizationid;
    }

//    public Organization getOrganization(Long id) {
//        return organizationRepository.findByOrganizationId(id).orElseThrow(() -> new RuntimeException("Organization not found with id: " + id));
//    }

    // 사용자 인증을 수행하지 않고 직접 OrganizationId 찾기
    private Long findOrganizationIdByUsername(String username) {
        return organizationJpaRepository.findByOrganizationId(username)
                .map(Organization::getId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND));
    }
//    public void updateProfile(Organization organization, String profileUrl) {
//        organization.setProfileUrl(profileUrl);
//        organizationJpaRepository.save(organization);
//    }
//
//    public void changePassword(Organization organization, String password) {
//        String encodedPassword = passwordEncoder.encode(password);
//        organization.setPassword(encodedPassword);
//        organizationJpaRepository.save(organization);
//    }


}
