package com.zoi4erom.strategygame.repository;

import com.zoi4erom.strategygame.entity.PlayerResource;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerResourcesRepository extends JpaRepository<PlayerResource, Long> {

	Optional<PlayerResource> findByGame_IdAndUser_Id(Long gameId, Long userId);
}
