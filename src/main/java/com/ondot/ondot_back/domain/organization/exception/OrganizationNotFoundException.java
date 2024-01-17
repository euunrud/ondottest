package com.ondot.ondot_back.domain.organization.exception;

import org.springframework.http.HttpStatus;

import com.ondot.ondot_back.global.common.exception.CustomException;

public class OrganizationNotFoundException extends CustomException {

	public OrganizationNotFoundException(HttpStatus errorCode) {
		super(errorCode);
	}
}
