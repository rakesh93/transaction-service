package com.ilearn.transaction_service.client_call;

import java.util.List;

public class AccountResponse {
	
	private int statusCode;
	private String message;
	private List<String> result;
	
	public AccountResponse(int statusCode, String message, List<String> result) {
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

	public List<String> getResult() {
		return result;
	}

	public void setResult(List<String> result) {
		this.result = result;
	}
}
