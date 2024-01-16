package com.ondot.ondot_back.domain.organization.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrganizationType {

	STUDENT_COUNCIL("STUDENT_COUNCIL"),
	STUDENT_CLUB("STUDENT_CLUB"),
	ACADEMIC_CLUB("ACADEMIC_CLUB");

	final String value;
}
