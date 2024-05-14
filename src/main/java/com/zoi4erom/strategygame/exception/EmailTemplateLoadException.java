package com.zoi4erom.strategygame.exception;

/**
 * Exception indicating that an error occurred while loading an email template.
 */
public class EmailTemplateLoadException extends RuntimeException {

	/**
	 * Constructs a new EmailTemplateLoadException with the specified error message.
	 *
	 * @param message The error message describing the cause of the exception.
	 */
	public EmailTemplateLoadException(String message) {
		super(message);
	}
}