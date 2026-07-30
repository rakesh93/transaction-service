package com.ilearn.transaction_service.exception;

import java.util.Collections;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ilearn.transaction_service.util.ApiResponse;
import com.ilearn.transaction_service.util.AppConstants;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ApiResponse handleValidation(MethodArgumentNotValidException ex) {
		String message = ex.getBindingResult().getFieldError().getDefaultMessage();
		return new ApiResponse(AppConstants.FAILURE, message, Collections.emptyList());
	}

}
