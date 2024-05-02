package com.zoi4erom.strategygame.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VerificationDto {

	private String email;
	private String code;
}
