package com.codewithashish.blog.services;

import java.util.List;

import com.codewithashish.blog.payloads.UserDto;

public interface UserService {
	
	
	UserDto registerNewUser(UserDto userDto);
	UserDto createUser(UserDto userDto);
	UserDto updateUser(UserDto userDto, Integer id);
	UserDto getUserById(Integer id);
	List<UserDto> getAllUsers();
	void deleteUser(Integer id);
	
}
