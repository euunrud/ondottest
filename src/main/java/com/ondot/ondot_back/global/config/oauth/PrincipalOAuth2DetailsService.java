package com.ondot.ondot_back.global.config.oauth;

import com.ondot.ondot_back.domain.organization.entity.Organization;
import com.ondot.ondot_back.domain.organization.repository.OrganizationJpaRepository;
import com.ondot.ondot_back.global.config.auth.PrincipalDetails;
import com.ondot.ondot_back.global.config.oauth.provider.GoogleUserInfo;
import com.ondot.ondot_back.global.config.oauth.provider.OAuth2UserInfo;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrincipalOAuth2DetailsService extends DefaultOAuth2UserService {
    private final OrganizationJpaRepository organizationJpaRepository;

    public PrincipalOAuth2DetailsService(OrganizationJpaRepository organizationJpaRepository) {
        this.organizationJpaRepository = organizationJpaRepository;
    }

    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        return processOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            System.out.println("구글 로그인 요청~~");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }

        Optional<Organization> userOptional =
                organizationJpaRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());

        Organization organization;
        if (userOptional.isPresent()) {
            organization = userOptional.get();
            System.out.println("구글 로그인 기록이 있습니다.");
            organization.setOrganizationId(oAuth2UserInfo.getEmail());
            organizationJpaRepository.save(organization);
        } else {
            System.out.println("구글 로그인이 최초입니다. 회원가입을 진행합니다.");
            organization = Organization.builder()
                    .organizationId(oAuth2UserInfo.getEmail())
                    .name(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId())
                    .provider(oAuth2UserInfo.getProvider())
                    .providerId(oAuth2UserInfo.getProviderId())
                    .build();
            organizationJpaRepository.save(organization);
        }

        return new PrincipalDetails(organization, oAuth2User.getAttributes());

    }
}
