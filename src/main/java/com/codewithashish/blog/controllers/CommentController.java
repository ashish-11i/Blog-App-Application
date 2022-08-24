package com.codewithashish.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithashish.blog.payloads.CommentDto;
import com.codewithashish.blog.response.model.ResponseMessage;
import com.codewithashish.blog.services.CommentService;

@RestController
@RequestMapping("/api/post/{postId}/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@PostMapping("/comments")
	public ResponseEntity<ResponseMessage> createComment(@RequestBody CommentDto commentDto, @PathVariable("postId") Integer postId){
		CommentDto commentDto2 = this.commentService.createComment(commentDto, postId);
		return new ResponseEntity<ResponseMessage>(new ResponseMessage(true, 200, commentDto2, null, "You have commented successfully"), HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable("commentId") Integer commentId){
		this.commentService.deleteComment(commentId);
		return null;
	}
}
