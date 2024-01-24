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
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;

//    @Autowired
//    public PrincipalOAuth2DetailsService(OrganizationJpaRepository organizationJpaRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
//        this.organizationJpaRepository = organizationJpaRepository;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//    }

    //구글로부터 받은 userRequest 데이터에 대한 후처리되는 함수
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        System.out.println("getClientRegistraition : " + userRequest.getClientRegistration().getRegistrationId()); // -> google
        System.out.println("getAccessToken : " + userRequest.getAccessToken());
        //구글로그인 버튼 클릭-> 구글로그인창-> 로그인을 완료-> code를 리턴(OAuth-Client라이브러리) -> AccessToken요청
        //userRequest정보 -> loadUser 함수 호출 -> 구글로부터 회원프로필 받아준다.
        System.out.println("getAttributes : " + super.loadUser(userRequest).getAttributes());


        OAuth2User oAuth2User = super.loadUser(userRequest); // google의 회원 프로필 조회

        return processOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

        OAuth2UserInfo oAuth2UserInfo = null; // Attribute를 파싱해서 공통 객체로 묶는다. 관리가 편함.

        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            System.out.println("구글 로그인 요청~~");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }

        Optional<Organization> userOptional =
                organizationJpaRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());

        Organization organization;
        if (userOptional.isPresent()) {
            organization = userOptional.get();
            // user가 존재하면 update 해주기
            System.out.println("구글 로그인 기록이 있습니다.");
            organization.setOrganizationId(oAuth2UserInfo.getEmail());
            organizationJpaRepository.save(organization);
        } else {
            System.out.println("구글 로그인이 최초입니다. 회원가입을 진행합니다.");
            // user의 패스워드가 null이기 때문에 OAuth 유저는 일반적인 로그인을 할 수 없음.
            organization = Organization.builder()
                    .organizationId(oAuth2UserInfo.getEmail())
                    .name(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId())
//                    .role("ROLE_USER")
//                    .password(bCryptPasswordEncoder.encode(oAuth2UserInfo.getEmail()))
                    .provider(oAuth2UserInfo.getProvider())
                    .providerId(oAuth2UserInfo.getProviderId())
                    .build();
            organizationJpaRepository.save(organization);
        }

        return new PrincipalDetails(organization, oAuth2User.getAttributes());

    }
}
