package com.ondot.ondot_back.global.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ondot.ondot_back.global.common.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler({CustomException.class})
	public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException e) {
		log.warn("[*] CustomException: ", e);
		HttpStatus errorCode = e.getErrorCode();
		return ResponseEntity.status(errorCode).body(
			ApiResponse.onFailure(
				String.valueOf(errorCode.value()),
				errorCode.getReasonPhrase()
			)
		);
	}
}
