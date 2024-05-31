package com.zoi4erom.strategygame.repository;

import com.zoi4erom.strategygame.entity.Unit;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<Unit, Long> {

	List<Unit> findAllByGame_Id(Long gameId);

	List<Unit> findAllByUser_IdAndGame_Id(Long userId, Long gameId);
}

