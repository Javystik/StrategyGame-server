package com.zoi4erom.strategygame.service.contract;

import com.zoi4erom.strategygame.dto.AuthRequest;

public interface AuthService {

	String authenticate(AuthRequest authRequest);

	String createUser(AuthRequest authRequest);
}
