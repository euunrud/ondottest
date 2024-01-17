package com.ondot.ondot_back.domain.organization.repository;

import com.ondot.ondot_back.domain.organization.entity.Organization;

public interface OrganizationRepository {

	Organization save(Organization organization);

	Organization findByOrganizationId(String email);
}
