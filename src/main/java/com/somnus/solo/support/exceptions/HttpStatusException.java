package com.somnus.solo.support.exceptions;

public class HttpStatusException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public HttpStatusException() {
		super();		
	}

	public HttpStatusException(String message, Throwable cause) {
		super(message, cause);		
	}

	public HttpStatusException(String message) {
		super(message);		
	}

	public HttpStatusException(Throwable cause) {
		super(cause);
	}
	
}
