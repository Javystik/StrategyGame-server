package com.zoi4erom.strategygame.service.impl;

import com.zoi4erom.strategygame.dto.MessageDto;
import com.zoi4erom.strategygame.entity.Game;
import com.zoi4erom.strategygame.entity.Message;
import com.zoi4erom.strategygame.entity.User;
import com.zoi4erom.strategygame.mapper.MessageMapper;
import com.zoi4erom.strategygame.repository.MessageRepository;
import com.zoi4erom.strategygame.service.contract.MessageService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

	private final MessageMapper messageMapper;
	private final MessageRepository messageRepository;
	private final SimpMessagingTemplate messagingTemplate;

	@Override
	public boolean createMessage(MessageDto messageDto) {
		try {
			messageRepository.save(Message.builder()
			    .text(messageDto.getText())
			    .user(User.builder().id(messageDto.getUserId()).build())
			    .game(Game.builder().id(messageDto.getGameId()).build())
			    .build());

			messageDto.setType("messages");
			messagingTemplate.convertAndSend("/topic/game/" + messageDto.getGameId(), messageDto);

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<MessageDto> getAllMessageByGameId(Long gameId) {
		return messageRepository.findMessageByGame_id(gameId).stream()
		    .map(message -> {
			    MessageDto messageDto = messageMapper.toDto(message);
			    if (message.getUser().getAlliance() != null) {
				    messageDto.setClanTag(message.getUser().getAlliance().getTag());
			    }
			    return messageDto;
		    })
		    .toList();
	}
}
