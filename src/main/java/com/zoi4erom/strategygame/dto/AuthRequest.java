package com.zoi4erom.strategygame.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthRequest {

	private String username;
	private String password;
	private String email;
}
