package com.zoi4erom.strategygame.service.contract;

import com.zoi4erom.strategygame.dto.MessageDto;
import java.util.List;

public interface MessageService {

	boolean createMessage(MessageDto messageDto);

	List<MessageDto> getAllMessageByGameId(Long gameId);
}
