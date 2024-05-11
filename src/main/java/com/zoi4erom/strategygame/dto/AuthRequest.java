package com.zoi4erom.strategygame.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AuthRequest {

	private String username;
	private String password;
	private String email;
}
