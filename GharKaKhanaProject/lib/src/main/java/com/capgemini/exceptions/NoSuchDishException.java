package com.capgemini.exceptions;

public class NoSuchDishException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSuchDishException(String message) {
		super(message);
	}
}
