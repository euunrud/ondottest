package com.ondot.ondot_back.domain.organization.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ondot.ondot_back.domain.organization.entity.Organization;

public interface OrganizationJpaRepository extends JpaRepository<Organization, Long> {

	Optional<Organization> findByOrganizationId(String organizationId);

	Organization findSingleByOrganizationId(String organizationId);

	// SELECT * FROM user WHERE provider = ?1 and providerId = ?2
	Optional<Organization> findByProviderAndProviderId(String provider, String providerId);

	boolean existsByOrganizationId(String organizationId);

}
