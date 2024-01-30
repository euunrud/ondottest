package com.ondot.ondot_back.global.config.auth;

import com.ondot.ondot_back.domain.organization.entity.Organization;
import com.ondot.ondot_back.domain.organization.repository.OrganizationJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private OrganizationJpaRepository organizationRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Organization organization = organizationRepository.findSingleByOrganizationId(username);
        if(organization == null){
            return null;
        } else {
            return new PrincipalDetails(organization);
        }
    }
}
