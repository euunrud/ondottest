package com.ondot.ondot_back.global.config.auth;

import com.ondot.ondot_back.domain.organization.entity.Organization;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

//로그인 진행이 완료가 되면 session을 만들어줍니다. (Security ContextHolder)
//오브젝트 타입=> Authentication 타입 객체
//Authentication 안에 User 정보가 있어야 됨.
//User오브젝트타입=>UserDetails타입 객체
//Security Session => Authentication=>UserDetails(PrincipalDetails)

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private Organization orgaization;
    private Map<String, Object> attributes;

    //일반 시큐리티 로그인시 사용
    public PrincipalDetails(Organization orgaization) {
        this.orgaization = orgaization;
    }
    //OAuth2.0 로그인시 사용
    public PrincipalDetails(Organization orgaization,  Map<String, Object> attributes) {
        this.orgaization = orgaization;
        this.attributes = attributes;
    }

    //해당 User의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
//                return orgaization.getRole();
                return null;
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return orgaization.getPassword();
    }

    @Override
    public String getUsername() {
        return orgaization.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        //사이트에서 1년동안 회원 로그인을 안하면 휴먼 계정 설정하려면 추가

        return true;
    }

    // 리소스 서버로 부터 받는 회원정보
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    // orgaization의 PrimaryKey
    @Override
    public String getName() {
        return orgaization.getId()+"";
    }
}
