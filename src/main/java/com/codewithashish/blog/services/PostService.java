package com.codewithashish.blog.services;

import java.util.List;

import com.codewithashish.blog.payloads.PostDto;
import com.codewithashish.blog.payloads.PostResponse;

public interface PostService {
	//create
	PostDto createPostWuthOnlyBody(PostDto postDto);
	
	//create 
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	//update
	PostDto updatePost(PostDto postDto, Integer postId);
	
	//delete
	void deletePost(Integer postId);
	
	//get all post
	List<PostDto> getAllPost();
	
	PostResponse getPostsByPagignation(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	//get single post
	PostDto getPostById(Integer postId);
	
	//get all posts by category
	List<PostDto> getPostByCategory(Integer categoryId);
	
	//get all posts by user
	List<PostDto> getPostByUser(Integer userId);
	
	//search posts
	List<PostDto> searchPosts(String keyword);
}
