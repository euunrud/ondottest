package com.ondot.ondot_back.domain.organization.repository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.ondot.ondot_back.domain.organization.entity.Organization;
import com.ondot.ondot_back.domain.organization.exception.OrganizationNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class OrganizationRepositoryImpl implements OrganizationRepository {

	private final OrganizationJpaRepository organizationJpaRepository;

	@Override
	public Organization save(Organization organization) {
		return organizationJpaRepository.save(organization);
	}

	@Override
	public Organization findByOrganizationId(String organizationId) {
		return organizationJpaRepository.findByOrganizationId(organizationId)
			.orElseThrow(() -> new OrganizationNotFoundException(HttpStatus.NOT_FOUND));
	}
}
