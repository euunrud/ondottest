package com.ondot.ondot_back.domain.organization.dto;

import com.ondot.ondot_back.domain.organization.enums.OrganizationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthSignupRequest { //일반 회원가입
    private String organizationId;
    private String name;
    private String password;
    private OrganizationType type;
    private String profileUrl;
}
