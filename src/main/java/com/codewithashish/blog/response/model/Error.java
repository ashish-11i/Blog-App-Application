package com.codewithashish.blog.response.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Error {
	private String errorCode;
	private Object errorMesaage;
//	public Error(String errorCode, String errorMesaage) {
//		super();
//		this.errorCode = errorCode;
//		this.errorMesaage = errorMesaage;
//	}
}
