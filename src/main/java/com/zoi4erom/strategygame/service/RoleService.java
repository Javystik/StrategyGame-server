package com.zoi4erom.strategygame.service;

import com.zoi4erom.strategygame.entity.Role;
import com.zoi4erom.strategygame.exception.RoleNotFoundException;
import com.zoi4erom.strategygame.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

	private final RoleRepository roleRepository;

	public Role getUserRole() {
		return roleRepository.findRoleByName("ROLE_USER")
		    .orElseThrow(() -> new RoleNotFoundException("ROLE_USER"));
	}
}
