package com.ebox3.server.exception;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

	private final Log logger = LogFactory.getLog(getClass());

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorMessage> resourceNotFoundHanlder(ResourceNotFoundException ex, WebRequest request) {
		ErrorMessage err = new ErrorMessage(ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorMessage>(err, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ErrorMessage> unauthorizedHandler(UnauthorizedException ex, WebRequest request) {
		ErrorMessage err = new ErrorMessage(ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorMessage>(err, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<ErrorMessage> forbiddenHandler(ForbiddenException ex, WebRequest request) {
		ErrorMessage err = new ErrorMessage(ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorMessage>(err, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorMessage> badRequestHandler(BadRequestException ex, WebRequest request) {
		ErrorMessage err = new ErrorMessage(ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorMessage>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InternalServerException.class)
	public ResponseEntity<ErrorMessage> internalServerException(InternalServerException ex, WebRequest request) {
		ErrorMessage err = new ErrorMessage(ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorMessage>(err, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> handleValidationExceptions(MethodArgumentNotValidException ex,
			WebRequest request) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		ErrorMessage err = new ErrorMessage(ex.getMessage(), request.getDescription(false));
		err.setErrors(errors);
		return new ResponseEntity<ErrorMessage>(err, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {
		logger.error("ResponseStatusException occured", ex);
	    return ResponseEntity.status(ex.getStatusCode())
	            .body(Map.of(
	                "error", ex.getStatusCode().toString(),  // Der HTTP-Status-Code als String
	                "message", ex.getReason()               // Die Fehlernachricht
	            ));
	}
}
