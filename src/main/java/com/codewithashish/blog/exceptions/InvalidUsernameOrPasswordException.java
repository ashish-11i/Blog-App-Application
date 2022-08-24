package com.codewithashish.blog.exceptions;

public class InvalidUsernameOrPasswordException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public InvalidUsernameOrPasswordException(String message) {
		super(message);
	}

}
