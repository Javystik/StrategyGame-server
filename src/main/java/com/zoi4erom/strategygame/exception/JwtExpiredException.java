package com.zoi4erom.strategygame.exception;

public class JwtExpiredException extends RuntimeException {

	public JwtExpiredException(String message) {
		super(message);
	}
}
