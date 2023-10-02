package com.ebox3.server.exception;

import java.util.HashMap;
import java.util.Map;

public class ErrorMessage {

	private String message;
	private String details;

	private Map<String, String> errors = new HashMap<>();

	public ErrorMessage(String message, String details) {
		this.message = message;
		this.details = details;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

}
