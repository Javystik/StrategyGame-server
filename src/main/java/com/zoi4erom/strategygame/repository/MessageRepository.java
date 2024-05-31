package com.zoi4erom.strategygame.repository;

import com.zoi4erom.strategygame.entity.Message;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
	List<Message> findMessageByGame_id(Long gameId);
}