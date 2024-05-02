package com.zoi4erom.strategygame.exception;

public class AllianceNotFoundException extends RuntimeException {

	public AllianceNotFoundException(String identifier, String value) {
		super("Alliance with " + identifier + " '" + value + "' not found.");
	}
}