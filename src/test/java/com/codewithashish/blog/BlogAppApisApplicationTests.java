package com.codewithashish.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.codewithashish.blog.repository.UserRepo;

@SpringBootTest
class BlogAppApisApplicationTests {
	
	@Autowired
	private UserRepo userRepo;
	

	@Test
	void contextLoads() {
	}

	
	//We are checking userRepo
	@Test
	public void testRepo() {
		String className = userRepo.getClass().getName();
		String packName = userRepo.getClass().getPackageName();
		System.out.println(className);
		System.out.println(packName);
	}
}
