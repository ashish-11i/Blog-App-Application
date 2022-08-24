package com.codewithashish.blog.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithashish.blog.payloads.UserDto;
import com.codewithashish.blog.response.model.ResponseMessage;
import com.codewithashish.blog.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping({"/"})
	public ResponseEntity<ResponseMessage> createUser(@Valid @RequestBody UserDto userDto){
		UserDto createdUser = this.userService.createUser(userDto);
		return new ResponseEntity<>(new ResponseMessage(true, 200, createdUser, null, "Created new user in DB successfully"), HttpStatus.CREATED);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<ResponseMessage> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer userId){
		UserDto updatedUser = this.userService.updateUser(userDto, userId);
		return new ResponseEntity<>(new ResponseMessage(true, 200, updatedUser, null, "DB data updated successfully."), HttpStatus.OK);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<ResponseMessage> getUserById(@PathVariable("userId") Integer userId){
		UserDto user = this.userService.getUserById(userId);
		return new ResponseEntity<>(new ResponseMessage(true, 200, user, null, "DB data fetched successfully"), HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<ResponseMessage> getAllUser(){
		List<UserDto> user = this.userService.getAllUsers();
		return new ResponseEntity<>(new ResponseMessage(true, 200, user, null, "DB data fetched successfully."), HttpStatus.OK);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer userId){
		this.userService.deleteUser(userId);
		return new ResponseEntity<>(Map.of("message","User deleted successfully."), HttpStatus.OK);
	}
	
	
}
