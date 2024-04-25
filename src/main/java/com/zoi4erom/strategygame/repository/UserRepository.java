package com.zoi4erom.strategygame.repository;

import com.zoi4erom.strategygame.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
