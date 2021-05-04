package com.capgemini.exceptions;

public class NoSuchFoodItemException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSuchFoodItemException(String message) {
		super(message);
	}
}
