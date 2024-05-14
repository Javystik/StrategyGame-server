package com.zoi4erom.strategygame.exception;

/**
 * Exception indicating that an error occurred while sending an email.
 */
public class EmailSendingException extends RuntimeException {

	/**
	 * Constructs a new EmailSendingException with the specified error message.
	 *
	 * @param message The error message describing the cause of the exception.
	 */
	public EmailSendingException(String message) {
		super(message);
	}
}