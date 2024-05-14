package com.zoi4erom.strategygame.service.impl;

import com.zoi4erom.strategygame.entity.Role;
import com.zoi4erom.strategygame.exception.RoleNotFoundException;
import com.zoi4erom.strategygame.repository.RoleRepository;
import com.zoi4erom.strategygame.service.contract.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementation of the interface responsible for retrieving roles from the database. This
 * implementation provides methods for getting specific roles, such as the user role.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository; // Role repository

	/**
	 * Retrieves the user role from the database.
	 *
	 * @return The user role entity
	 * @throws RoleNotFoundException If the user role is not found in the database
	 */
	public Role getUserRole() {
		log.info("Getting role 'ROLE_USER'");
		return roleRepository.findRoleByName("ROLE_USER")
		    .orElseThrow(() -> {
			    log.error("Role 'ROLE_USER' not found");
			    return new RoleNotFoundException("ROLE_USER");
		    });
	}
}