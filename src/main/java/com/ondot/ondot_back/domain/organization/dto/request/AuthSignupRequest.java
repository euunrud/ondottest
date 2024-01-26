package com.ondot.ondot_back.domain.organization.dto.request;

import com.ondot.ondot_back.domain.organization.enums.OrganizationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthSignupRequest {
    private String organizationId;
    private String name;
    private String password;
    private OrganizationType type;
    private String profileUrl;
}
