package com.zoi4erom.strategygame.mapper;

import com.zoi4erom.strategygame.dto.MessageDto;
import com.zoi4erom.strategygame.entity.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper implements Mapper<Message, MessageDto>{

	@Override
	public MessageDto toDto(Message entity) {
		return MessageDto.builder()
		    .id(entity.getId())
		    .text(entity.getText())
		    .gameId(entity.getGame().getId())
		    .username(entity.getUser().getUsername())
		    .userId(entity.getUser().getId())
		    .build();
	}

	@Override
	public Message toEntity(MessageDto dto) {
		return Message.builder()
		    .id(dto.getId())
		    .text(dto.getText())
		    .build();
	}
}
