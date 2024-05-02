package com.zoi4erom.strategygame.exception;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(String identifier, String value) {
		super("User with " + identifier + " '" + value + "' not found.");
	}
}
