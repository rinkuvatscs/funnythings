package com.interview.validateException;

import org.springframework.http.HttpStatus;

public class ValidationException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HttpStatus httpStatus;
	
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	private String message;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	public ValidationException(){
		
	}
	public ValidationException(String message, HttpStatus httpStatus) {
		super();
		this.message = message;
		this.httpStatus = httpStatus;
	}
	
	
	
	

}
