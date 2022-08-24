package com.codewithashish.blog.response.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage {
	private boolean status;
	private int statusCode;
	private Object response;
	private Error error;
	private String message;
//	public ResponseMessage(boolean status, int statusCode, Object response, Error error, String message) {
//		super();
//		this.status = status;
//		this.statusCode = statusCode;
//		this.response = response;
//		this.error = error;
//		this.message = message;
//	}
	
}
