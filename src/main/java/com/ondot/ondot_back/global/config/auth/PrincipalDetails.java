package com.ondot.ondot_back.global.config.auth;

import com.ondot.ondot_back.domain.organization.entity.Organization;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private Organization orgaization;
    private Map<String, Object> attributes;

    public PrincipalDetails(Organization orgaization) {
        this.orgaization = orgaization;
    }

    public PrincipalDetails(Organization orgaization,  Map<String, Object> attributes) {
        this.orgaization = orgaization;
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
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
//        return orgaization.getName();
        return orgaization.getOrganizationId();
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
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return orgaization.getId()+"";
    }
}
