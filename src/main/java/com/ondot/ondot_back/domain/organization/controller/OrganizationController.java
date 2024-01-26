package com.ondot.ondot_back.domain.organization.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ondot.ondot_back.domain.organization.dto.response.OrganizationGetResponse;
import com.ondot.ondot_back.domain.organization.dto.request.OrganizationUpdateRequest;
import com.ondot.ondot_back.domain.organization.dto.response.OrganizationUpdateResponse;
import com.ondot.ondot_back.domain.organization.entity.Organization;
import com.ondot.ondot_back.domain.organization.service.OrganizationService;
import com.ondot.ondot_back.global.common.ApiResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/organizations")
public class OrganizationController {

	private final OrganizationService organizationService;

	@GetMapping("/{organizationId}")
	public ApiResponse<OrganizationGetResponse> getOrganization(@PathVariable String organizationId) {
		Organization findOrganization = organizationService.getOrganization(organizationId);
		return ApiResponse.onSuccess(
			OrganizationGetResponse.from(findOrganization)
		);
	}

	@PatchMapping("/{organizationId}")
	public ApiResponse<OrganizationUpdateResponse> updateOrganization(
		@PathVariable String organizationId,
		@RequestBody OrganizationUpdateRequest request) {
		Organization updateOrganization = organizationService.updateOrganization(organizationId, request);

		return ApiResponse.onSuccess(
			OrganizationUpdateResponse.from(updateOrganization)
		);
	}
}
