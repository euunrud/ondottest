package com.ondot.ondot_back.global.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

	private final HttpStatus errorCode;

	public CustomException(HttpStatus errorCode) {
		this.errorCode = errorCode;
	}
}
