package com.ondot.ondot_back.domain.organization.dto;

import com.ondot.ondot_back.domain.organization.entity.Organization;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record OrganizationGetResponse(
	@NonNull String name,
	@NonNull String type,
	@NonNull String profileUrl,
	@NonNull String imageUrl,
	@NonNull String contact,
	@NonNull String description
) {

	public static OrganizationGetResponse from(Organization organization) {
		return OrganizationGetResponse.builder()
			.name(organization.getName())
			.type(String.valueOf(organization.getType()))
			.profileUrl(organization.getProfileUrl())
			.imageUrl(organization.getImageUrl())
			.contact(organization.getContact())
			.description(organization.getDescription())
			.build();
	}
}
