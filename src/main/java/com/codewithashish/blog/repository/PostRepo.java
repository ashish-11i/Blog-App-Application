package com.codewithashish.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codewithashish.blog.entities.Category;
import com.codewithashish.blog.entities.Post;
import com.codewithashish.blog.entities.User;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {
	
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	List<Post> findByTitleContaining(String keyword);
}
