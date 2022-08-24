package com.codewithashish.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codewithashish.blog.entities.User;
import com.codewithashish.blog.exceptions.ResourceNotFoundException;
import com.codewithashish.blog.repository.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = this.userRepo.findUserByUserName(username).orElseThrow(()-> new ResourceNotFoundException("User", username, username));
		return new org.springframework.security.core.userdetails.User(username, user.getUserPassword(), user.getAuthorities());
	}

}
