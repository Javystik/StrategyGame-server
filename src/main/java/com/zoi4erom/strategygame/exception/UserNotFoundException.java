package com.zoi4erom.strategygame.exception;

/**
 * Exception indicating that a user with the specified identifier was not found.
 */
public class UserNotFoundException extends RuntimeException {

	/**
	 * Constructs a new UserNotFoundException with the specified identifier and value.
	 *
	 * @param identifier The identifier indicating the type of value being searched for (e.g.,
	 *                   "username", "email", etc.).
	 * @param value      The value of the identifier that was not found.
	 */
	public UserNotFoundException(String identifier, String value) {
		super("User with " + identifier + " '" + value + "' not found.");
	}
}
