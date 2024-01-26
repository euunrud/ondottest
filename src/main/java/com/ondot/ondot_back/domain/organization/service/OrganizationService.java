package com.ondot.ondot_back.domain.organization.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ondot.ondot_back.domain.organization.dto.request.OrganizationUpdateRequest;
import com.ondot.ondot_back.domain.organization.entity.Organization;
import com.ondot.ondot_back.domain.organization.repository.OrganizationRepository;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrganizationService {

	private final OrganizationRepository organizationRepository;

	public Organization getOrganization(String organizationId) {
		return organizationRepository.findByOrganizationId(organizationId);
	}

	@Transactional
	public Organization updateOrganization(String organizationId, OrganizationUpdateRequest request) {
		Organization findOrganization = organizationRepository.findByOrganizationId(organizationId);
		return findOrganization.update(request);
	}
}
