package com.zoi4erom.strategygame.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "message")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String text;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "game_id")
	private Game game;
}
