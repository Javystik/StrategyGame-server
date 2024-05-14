package com.zoi4erom.strategygame.exception;

/**
 * Exception indicating that a role with the specified name was not found.
 */
public class RoleNotFoundException extends RuntimeException {

	/**
	 * Constructs a new RoleNotFoundException with the specified role name.
	 *
	 * @param roleName The name of the role that was not found.
	 */
	public RoleNotFoundException(String roleName) {
		super("Role '" + roleName + "' not found.");
	}
}