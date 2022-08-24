package com.codewithashish.blog.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithashish.blog.exceptions.InvalidUsernameOrPasswordException;
import com.codewithashish.blog.payloads.JwtAuthRequest;
import com.codewithashish.blog.payloads.JwtAuthResponse;
import com.codewithashish.blog.payloads.UserDto;
import com.codewithashish.blog.response.model.ResponseMessage;
import com.codewithashish.blog.security.CustomUserDetailsService;
import com.codewithashish.blog.security.JwtTokenHelper;
import com.codewithashish.blog.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> craeteToken(@RequestBody JwtAuthRequest jwtAuthRequest)  {

		this.authenticate(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword());
		UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(jwtAuthRequest.getUsername());
		String token = jwtTokenHelper.generateToken(userDetails);
		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		jwtAuthResponse.setToken(token);
		return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse, HttpStatus.OK);
	}

	private void authenticate(String username, String password)  {

		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				username, password);

		try {
			this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		}catch(BadCredentialsException e) {
			throw new InvalidUsernameOrPasswordException("Username or password is not valid");
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<ResponseMessage> registerNewUser(@Valid @RequestBody UserDto userDto) {
		UserDto registeredUser =  this.userService.registerNewUser(userDto);
		return new ResponseEntity<>(new ResponseMessage(true, 200, registeredUser, null, "You have registered successfully"), HttpStatus.CREATED);
	}
}
