package com.ondot.ondot_back.domain.organization.dto.request;

public record OrganizationUpdateRequest(
	String name,
	String type,
	String profileUrl,
	String imageUrl,
	String contact,
	String description
) {
}
