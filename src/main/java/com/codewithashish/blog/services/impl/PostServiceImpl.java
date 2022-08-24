package com.codewithashish.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.codewithashish.blog.entities.Category;
import com.codewithashish.blog.entities.Post;
import com.codewithashish.blog.entities.User;
import com.codewithashish.blog.exceptions.ResourceNotFoundException;
import com.codewithashish.blog.payloads.PostDto;
import com.codewithashish.blog.payloads.PostResponse;
import com.codewithashish.blog.repository.CategoryRepo;
import com.codewithashish.blog.repository.PostRepo;
import com.codewithashish.blog.repository.UserRepo;
import com.codewithashish.blog.services.PostService;
import org.springframework.data.domain.*;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		
		User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));
		Category category = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));
		
		Post post = modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post savedPost = postRepo.save(post); 
		
		PostDto resultDto = this.modelMapper.map(savedPost, PostDto.class);
		return resultDto;
	}

	//Below create method have some error

	@Override
	public PostDto createPostWuthOnlyBody(PostDto postDto) {

//		System.out.println("Services PostDto  "+postDto);
//		Integer userId = postDto.getUserDto().getId();
//		User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));
//		
//		Integer categoryId = postDto.getCategoryDto().getCategoryId();
//		Category category = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));
//		
//		Post post = this.modelMapper.map(postDto, Post.class);
//		post.setImageName("default.png");
//		post.setAddedDate(new Date());
//		
//		post.setUser(user);
//		
//		post.setCategory(category);
//		Post savedPost = postRepo.save(post);
//		return this.modelMapper.map(savedPost, PostDto.class);
		return null;
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post existPost = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
		existPost.setTitle(postDto.getTitle());
		existPost.setContent(postDto.getContent());
		existPost.setImageName(postDto.getImageName());
		Post post =this.postRepo.save(existPost);
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
		postRepo.delete(post);
	}

	@Override
	public List<PostDto> getAllPost() {
		List<Post> posts = postRepo.findAll();
		List<PostDto> postDtos = posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}
	
	@Override
	public PostResponse getPostsByPagignation(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		
//		
//		Sort sort = null;
//		
//		if(sortDir.equalsIgnoreCase("asc")) {
//			sort=Sort.by(sortBy).ascending();
//		}else {
//			sort = Sort.by(sortBy).descending();
//		}
		
		Sort sort = sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = this.postRepo.findAll(pageable);
		List<Post> posts = pagePost.getContent();
		List<PostDto> postDtos = posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
		
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		 Category category = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));
		 List<Post> posts = this.postRepo.findByCategory(category);
		 List<PostDto> postDtos = posts.stream().map((post)->
			this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		 
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));
		List<Post> posts = postRepo.findByUser(user);
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos = posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	

	
	

}
