package com.zoi4erom.strategygame.repository;

import com.zoi4erom.strategygame.entity.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findRoleByName(String roleName);
}
