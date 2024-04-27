package com.zoi4erom.strategygame.service;

import com.zoi4erom.strategygame.entity.Role;
import com.zoi4erom.strategygame.repository.RoleRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

	private final RoleRepository roleRepository;

	public Optional<Role> findRoleByName(String roleName) {
		return roleRepository.findRoleByName(roleName);
	}
}
