package com.ilearn.transaction_service.util;

public class ApiResponse {

	private int statusCode;
	private String message;
	private Object result;

	public ApiResponse(int statusCode, String message, Object result) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.result = result;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setResult(Object result) {
		this.result= result;
	}
	public Object getResult() {
		return result;
	}

	@Override
	public String toString() {
		return "ApiResponse [statusCode=" + statusCode + ", message=" + message + ", result=" + result + "]";
	}

	
}
