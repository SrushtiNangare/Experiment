package com.capgemini.exceptions;

import java.util.NoSuchElementException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice // autodetected by @componentscan public class
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(NoSuchAdminException.class)
	// handling exception
	public ResponseEntity<Object> AdminException(NoSuchAdminException exception) {
		return new ResponseEntity<>(" No Such Admin Found ", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<Object> AdminException(NoSuchElementException exception) {
		return new ResponseEntity<>(" No Value is present in DB, Please change the input ", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NoSuchVendorException.class)
	// handling exception
	public ResponseEntity<Object> VendorException(NoSuchVendorException exception) {
		return new ResponseEntity<>(" No Such Vendor Found ", HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(NoSuchOrderException.class)
	// handling exception
	public ResponseEntity<Object> OrderException(NoSuchOrderException exception) {
		return new ResponseEntity<>(" No Such Order Found ", HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(NoSuchCustomerException.class)
	// handling exception
	public ResponseEntity<Object> CustomerException(NoSuchCustomerException exception) {
		return new ResponseEntity<>(" Customer Not Found ", HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(NoSuchDishException.class)
	// handling exception
	public ResponseEntity<Object> DishException(NoSuchDishException exception) {
		return new ResponseEntity<>(" No Such Dish Found ", HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(NoSuchFoodItemException.class)
	// handling exception
	public ResponseEntity<Object> FoodItemException(NoSuchFoodItemException exception) {
		return new ResponseEntity<>(" No Such FoodItem Present ", HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<>(" Please Check your Http method type ", HttpStatus.INTERNAL_SERVER_ERROR);

	}
}