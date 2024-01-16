package com.user.ums.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.user.ums.exception.UserAlreadyExistsException;
import com.user.ums.exception.UserNotFoundByIdException;

@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler{
	
	
	private ResponseEntity<Object> structure(HttpStatus status,String message,Object rootcause){
		return new ResponseEntity<Object>(Map.of("status",status.value(),
				"message",message,"rootcause",
				rootcause),status);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		java.util.List<ObjectError> allErrors= ex.getAllErrors();
		
		Map<String,String> errors=new HashMap<String,String>();
		
		allErrors.forEach(error ->{
			FieldError fieldError=(FieldError)error;
			errors.put(fieldError.getField(),fieldError.getDefaultMessage());
		});
		return structure(HttpStatus.BAD_REQUEST, "failed to save the data", errors);
	}
	
	@ExceptionHandler(UserNotFoundByIdException.class)
	public ResponseEntity<Object> handleUserNotFoundByIdException(UserNotFoundByIdException ex){
		return structure(HttpStatus.NOT_FOUND, ex.getMessage(), "User id not found");
	}
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<Object> handleUserAlreadyExistException(UserAlreadyExistsException ex){
		return structure(HttpStatus.BAD_REQUEST, ex.getMessage(), "Email already exists");
	}
}
