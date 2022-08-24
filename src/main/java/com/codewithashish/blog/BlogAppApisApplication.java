package com.codewithashish.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.codewithashish.blog.config.AppConstants;
import com.codewithashish.blog.entities.Role;
import com.codewithashish.blog.repository.RoleRepo;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner{
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}
//
//	@Autowired
//	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	//For this we use its maven dependency
	//we used Bean thats why Now we can autowired of it.
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}


	@Override
	public void run(String... args) throws Exception {

		try {
			Role role1 = new Role();
			role1.setId(AppConstants.ADMIN_USER);
			role1.setName("ROLE_ADMIN");
			
			Role role2 = new Role();
			role2.setId(AppConstants.NORMAL_USER);
			role2.setName("ROLE_NORMAL");
			
			List<Role> roles = List.of(role1, role2);
//			List<Role> result =  roleRepo.saveAll(roles);
			roleRepo.saveAll(roles);
//			result.forEach(role->{
//				System.out.println(role.getName());
//			});
			
		}catch(Exception e) {
			e.printStackTrace();
		}
//		System.out.println(this.passwordEncoder.encode("ashish@pass"));
	}
	
}
