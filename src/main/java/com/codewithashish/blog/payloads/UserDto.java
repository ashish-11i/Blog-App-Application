package com.codewithashish.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.codewithashish.blog.config.AppConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


public class UserDto {

	private Integer id;
	@NotEmpty()
	@Size(min = 3, max = 60, message = "First Name must be min of 3 characters and max of 60 characters !!")
	@Pattern(regexp = AppConstants.NAME_REGEX)
	private String firstName;
	
	@Size(message = "Last Name should not be greater than 60")
	@Pattern(regexp = AppConstants.NAME_REGEX)
	private String lastName;
	
	@NotEmpty(message = "User name is invalid")
	private String userName;
	
	@Email(message = "Email is not valid!!")
	@Pattern(regexp = "^[a-zA-Z]+([0-9]+)?[\\.]?([a-zA-Z0-9]+)?[\\@][a-zA-Z]+[\\.][a-zA-Z]{2,3}$", message = "must be in email formate")
	private String email;
	
	@NotEmpty
	@Size(min = 8, max = 16, message = "Password must be min of 8 characters and max of 16 characters!!")
	private String userPassword;
	@NotEmpty
	
	private String about;
	private Set<RoleDto> roles = new HashSet<>();
	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserDto(Integer id,
			@NotEmpty @Size(min = 3, max = 60, message = "First Name must be min of 3 characters and max of 60 characters !!") String firstName,
			@Size(message = "Last Name should not be greater than 60") String lastName,
			@NotEmpty(message = "User name is invalid") String userName,
			@Email(message = "Email is not valid!!") String email,
			@NotEmpty @Size(min = 8, max = 16, message = "Password must be min of 8 characters and max of 16 characters!!") String userPassword,
			@NotEmpty String about, Set<RoleDto> roles) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.email = email;
		this.userPassword = userPassword;
		this.about = about;
		this.roles = roles;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	//If you dont want to show password to the client side then use it
	@JsonIgnore
	@JsonProperty(value = "userPassword")
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public Set<RoleDto> getRoles() {
		return roles;
	}
	public void setRoles(Set<RoleDto> roles) {
		this.roles = roles;
	}
	
	
}
