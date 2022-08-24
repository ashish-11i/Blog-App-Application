package com.codewithashish.blog.config;

public class AppConstants {
	public static final String PAGE_NUMBER = "0";
	public static final String PAGE_SIZE = "10";
	public static final String SORT_BY = "postId";
	public static final String SORT_DIR	 = "asc";
	public static final long JWT_TOKEN_VALIDITY = 1000 * 60 * 60; 
	public static final Integer NORMAL_USER = 502; 
	public static final Integer ADMIN_USER = 501; 
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String SIGNING_KEY = "secret";
	
	
	public static final String NAME_REGEX = "^[a-zA-Z]+";
	public static final String EMAIL_REGEX = "^[a-zA-Z]+([0-9]+)?[\\.]?([a-zA-Z0-9]+)?[\\@][a-zA-Z]+[\\.][a-zA-Z]{2,3}$";
	public static final String MOBILE_NUMBER_REGEX="^(\\+91-? ?)?[9876][0-9]{2}-? ?[0-9]{3}-? ?[0-9]{4}$";
	

}
