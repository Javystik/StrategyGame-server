package com.zoi4erom.strategygame.exception;

/**
 * Exception indicating that an Alliance entity could not be found.
 */
public class AllianceNotFoundException extends RuntimeException {

	/**
	 * Constructs a new AllianceNotFoundException with the specified identifier and value.
	 *
	 * @param identifier The identifier used in the search query (e.g., "id" or "name").
	 * @param value      The value associated with the specified identifier.
	 */
	public AllianceNotFoundException(String identifier, String value) {
		super("Alliance with " + identifier + " '" + value + "' not found.");
	}
}