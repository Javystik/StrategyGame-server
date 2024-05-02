package com.zoi4erom.strategygame.exception;

public class RoleNotFoundException extends RuntimeException {

	public RoleNotFoundException(String roleName) {
		super("Role '" + roleName + "' not found.");
	}
}