package com.zoi4erom.strategygame.repository;

import com.zoi4erom.strategygame.entity.User;
import com.zoi4erom.strategygame.spec.UserSpecification;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

	Optional<User> findUserByUsername(String username);

	Optional<User> findUserByEmail(String email);
}
