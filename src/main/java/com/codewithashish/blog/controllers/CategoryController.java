package com.codewithashish.blog.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithashish.blog.payloads.CategoryDto;
import com.codewithashish.blog.response.model.ResponseMessage;
import com.codewithashish.blog.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/")
	public ResponseEntity<ResponseMessage> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto createdCategory = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<>(new ResponseMessage(true,200, createdCategory,null, "Created DB data successfully"), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ResponseMessage> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable("id") Integer categoryId){
		CategoryDto updateDto =  this.categoryService.updateCategory(categoryDto, categoryId);
		return new ResponseEntity<>(new ResponseMessage(true,200, updateDto,null, "Created DB data successfully"), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseMessage> getCategoryById(@PathVariable("id") Integer categoryId){
		CategoryDto categoryDto = this.categoryService.getCategoryById(categoryId);
		return new ResponseEntity<>(new ResponseMessage(true, 200, categoryDto, null, "DB data updated successfully"), HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<ResponseMessage> getAllCategory(){
		List<CategoryDto> categoryDto = this.categoryService.getAllCategory();
		return new ResponseEntity<ResponseMessage>(new ResponseMessage(true, 200, categoryDto, null, "Fetched DB data successfully"), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable("id") Integer categoryId){
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<>(Map.of("message","category deleted successfully."), HttpStatus.OK);
	}
}
