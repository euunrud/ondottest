package com.ondot.ondot_back.domain.organization.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ondot.ondot_back.domain.organization.entity.Organization;

public interface OrganizationJpaRepository extends JpaRepository<Organization, Long> {

	Optional<Organization> findByOrganizationId(String organizationId);
}
