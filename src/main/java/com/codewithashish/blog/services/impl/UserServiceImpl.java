package com.codewithashish.blog.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codewithashish.blog.config.AppConstants;
import com.codewithashish.blog.entities.Role;
import com.codewithashish.blog.entities.User;
import com.codewithashish.blog.exceptions.ResourceNotFoundException;
import com.codewithashish.blog.exceptions.UserAlreadyExistException;
import com.codewithashish.blog.payloads.UserDto;
import com.codewithashish.blog.repository.RoleRepo;
import com.codewithashish.blog.repository.UserRepo;
import com.codewithashish.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public UserDto registerNewUser(UserDto userDto) {
		String userName = userDto.getUserName();
		Optional<User> existUser = this.userRepo.findUserByUserName(userName);
		if(!existUser.isEmpty()) {
			throw new UserAlreadyExistException(userName);
		}
		User user = this.modelMapper.map(userDto, User.class);
		user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
		Role role =  this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		User newUser =  this.userRepo.save(user);
		
		return this.modelMapper.map(newUser, UserDto.class);
	}
	
	@Override
	public UserDto createUser(UserDto userDto) {
		
		 User user = modelMapper.map(userDto, User.class);
		String password =  user.getUserPassword();
		user.setUserPassword(passwordEncoder.encode(password));
		User savedUser = userRepo.save(user);
		
		
		return modelMapper.map(savedUser, UserDto.class);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer id) {
		User user = userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User", "Id", id));
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setUserName(userDto.getUserName());
		user.setEmail(userDto.getEmail());
		user.setUserPassword(passwordEncoder.encode(userDto.getUserPassword()));
		user.setAbout(userDto.getAbout());
		User updatedUser = userRepo.save(user);
		return modelMapper.map(updatedUser, UserDto.class);
	}

	@Override
	public UserDto getUserById(Integer id) {
		User user = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "ID", id));
		User savedUser = userRepo.save(user);
		return modelMapper.map(savedUser, UserDto.class);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = userRepo.findAll();
		//Here we use stream api of lambda function.
		List<UserDto> userDtos = users.stream().map(user->this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer id) {

		User user = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "Id", id));
		this.userRepo.delete(user);
	}

}