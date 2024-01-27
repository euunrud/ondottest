package com.ondot.ondot_back.domain.organization.service;


import com.ondot.ondot_back.domain.organization.dto.request.AuthSignupRequest;
import com.ondot.ondot_back.domain.organization.dto.request.SigninRequest;
import com.ondot.ondot_back.domain.organization.dto.response.SigninResponse;
import com.ondot.ondot_back.domain.organization.entity.Organization;
import com.ondot.ondot_back.domain.organization.enums.OrganizationType;
import com.ondot.ondot_back.domain.organization.repository.OrganizationJpaRepository;
import com.ondot.ondot_back.global.common.exception.CustomException;
import com.ondot.ondot_back.global.config.auth.PrincipalDetails;
import com.ondot.ondot_back.global.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
@Lazy
@Service
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final OrganizationJpaRepository organizationJpaRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final OrganizationType organizationType;

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
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(signinRequest.getOrganizationId(), signinRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        PrincipalDetails userEntity = (PrincipalDetails) authentication.getPrincipal();
        Long organization_id = userEntity.getOrgaization().getId();
        String accessToken = jwtTokenProvider.createAccessToken(organization_id);

        return new SigninResponse(accessToken, " ");
    }


    public SigninResponse signinAuto(SigninRequest signinRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(signinRequest.getOrganizationId(), signinRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        PrincipalDetails userEntity = (PrincipalDetails) authentication.getPrincipal();
        Long organizationId = userEntity.getOrgaization().getId();

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
}
