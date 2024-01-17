package com.ondot.ondot_back.domain.organization.dto;

import com.ondot.ondot_back.domain.organization.entity.Organization;

import lombok.Builder;

@Builder
public record OrganizationUpdateResponse(
	String name,
	String type,
	String profileUrl,
	String imageUrl,
	String contact,
	String description
) {

	public static OrganizationUpdateResponse from(Organization organization) {
		return OrganizationUpdateResponse.builder()
			.name(organization.getName())
			.type(String.valueOf(organization.getType()))
			.profileUrl(organization.getProfileUrl())
			.imageUrl(organization.getImageUrl())
			.contact(organization.getContact())
			.description(organization.getDescription())
			.build();
	}
}
