package com.codewithashish.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithashish.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
	
}
