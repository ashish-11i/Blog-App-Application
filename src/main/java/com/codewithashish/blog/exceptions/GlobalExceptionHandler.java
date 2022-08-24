package com.codewithashish.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.codewithashish.blog.response.model.Error;
import com.codewithashish.blog.response.model.ResponseMessage;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ResponseMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		String message = ex.getMessage();
		return new ResponseEntity<>(new ResponseMessage(true, 200, null, new Error("NOT_FOUND", message), null), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResponseMessage> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex){
		Map<String, String> resp = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error)->{
			String fieldName = ((FieldError)error).getField();
			String errorMessage = error.getDefaultMessage();
			resp.put(fieldName, errorMessage);
		});;
//		String x = resp.toString();
		return new ResponseEntity<ResponseMessage>(new ResponseMessage(true, 200, null, new Error("INVALID_INPUT_DATA", resp), null), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidUsernameOrPasswordException.class)
	public ResponseEntity<?> invalidUsernameOrPasswordException(InvalidUsernameOrPasswordException message){
		return new ResponseEntity<>(message.getMessage(), HttpStatus.UNAUTHORIZED);
	}
	@ExceptionHandler(UserAlreadyExistException.class)
	public ResponseEntity<ResponseMessage> userAlreadyExistException(UserAlreadyExistException exception){
		return new ResponseEntity<ResponseMessage>(new ResponseMessage(true, 200, exception.getMessage(), new Error("USER_ALREADY_EXIST", "Please enter another username"), null), HttpStatus.BAD_REQUEST);
	}
}
