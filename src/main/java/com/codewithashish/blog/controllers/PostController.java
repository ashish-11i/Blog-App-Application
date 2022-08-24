package com.codewithashish.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codewithashish.blog.config.AppConstants;
import com.codewithashish.blog.payloads.PostDto;
import com.codewithashish.blog.payloads.PostResponse;
import com.codewithashish.blog.response.model.ResponseMessage;
import com.codewithashish.blog.services.FileService;
import com.codewithashish.blog.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	
	@Value("${project.image}")
	private String path;
	
	// Create Post
	@PostMapping("/user/{userId}/category/{cateId}/post")
	public ResponseEntity<ResponseMessage> createPost(@RequestBody PostDto postDto,
			@PathVariable("userId") Integer userId, @PathVariable("cateId") Integer categoryId) {
		System.out.println("controller       PostDto         " + postDto);
		PostDto createdPostDto = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<>(new ResponseMessage(true, 200, createdPostDto, null, "Created post successfully"),
				HttpStatus.CREATED);
	}

	// Below create method have some error
	@PostMapping("/user/category/post")
	public ResponseEntity<ResponseMessage> createPost(@RequestBody PostDto postDto) {
		System.out.println("controller       PostDto         " + postDto);
		PostDto createdpost = postService.createPostWuthOnlyBody(postDto);
		return new ResponseEntity<>(new ResponseMessage(true, 200, createdpost, null, "POst created"),
				HttpStatus.CREATED);
	}

	// Get Posts by User
	@GetMapping("/user/{userId}/post")
	public ResponseEntity<ResponseMessage> getPostByUser(@PathVariable("userId") Integer userId) {
		List<PostDto> postDtos = this.postService.getPostByUser(userId);
		return new ResponseEntity<>(new ResponseMessage(true, 200, postDtos, null, "Fetched DB data successfully"),
				HttpStatus.OK);
	}

	// Get Posts by Category
	@GetMapping("/user/category/{cateId}/post")
	public ResponseEntity<ResponseMessage> getPostByCategory(@PathVariable("cateId") Integer categoryId) {
		List<PostDto> postDtos = this.postService.getPostByCategory(categoryId);
		return new ResponseEntity<>(new ResponseMessage(true, 200, postDtos, null, "Fetched DB data succefully"),
				HttpStatus.OK);
	}

	// Get Post by postId
	@GetMapping("/posts/{postId}")
	public ResponseEntity<ResponseMessage> getPostById(@PathVariable("postId") Integer postId) {
		PostDto postDto = this.postService.getPostById(postId);
		return new ResponseEntity<>(new ResponseMessage(true, 200, postDto, null, "Fetched Posts Successfully."),
				HttpStatus.OK);
	}

	// Get All Posts
	@GetMapping("/posts/")
	public ResponseEntity<ResponseMessage> getAllPosts() {
		List<PostDto> postDtos = this.postService.getAllPost();
		return new ResponseEntity<>(new ResponseMessage(true, 200, postDtos, null, "Fetched Posts successfully"),
				HttpStatus.OK);
	}

	// Instead of doing above we can do the same thing by below code
	// Get Posts Using Paging
	@GetMapping("/posts/p")
	public ResponseEntity<ResponseMessage> getPostsByPagignation(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
		PostResponse postResponse = this.postService.getPostsByPagignation(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(new ResponseMessage(true, 200, postResponse, null, "Fetched Posts successfully"),
				HttpStatus.OK);
	}

	// Update post
	@PutMapping("/posts/{postId}")
	public ResponseEntity<ResponseMessage> updatePost(@RequestBody PostDto postDto,
			@PathVariable("postId") Integer postId) {
		PostDto post = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<>(new ResponseMessage(true, 200, post, null, "Post update in DB successfully"),
				HttpStatus.OK);
	}

	// delete post
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<Map<String, String>> deletePost(@PathVariable("postId") Integer postId) {
		this.postService.deletePost(postId);
		return ResponseEntity.ok(Map.of("message", "post deleted successfully"));
	}

	@GetMapping("posts/search/{keywords}")
	public ResponseEntity<ResponseMessage> searchPost(@PathVariable("keywords") String keywords) {
		List<PostDto> postDtos = this.postService.searchPosts(keywords);
		return new ResponseEntity<>(new ResponseMessage(true, 200, postDtos, null, "Searched Posts successfully"),
				HttpStatus.OK);
	}

	// Post Image Upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<ResponseMessage> uploadPostImage(
			@RequestParam("image") MultipartFile file,
			@PathVariable("postId") Integer postId) throws Exception {
		PostDto postDto = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, file);
		postDto.setImageName(fileName);
		PostDto updatePostDto = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<ResponseMessage>(new ResponseMessage(true, 200, updatePostDto, null, "Image uploaded successfully"), HttpStatus.OK);
	}
	
	//Method to serve files
	@GetMapping(value = "/post/image/{imageName}")
	public void downloadImage(
			@PathVariable String imageName,
			HttpServletResponse response) throws IOException {
		
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	

}
