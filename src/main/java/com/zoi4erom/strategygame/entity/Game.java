package com.zoi4erom.strategygame.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "game")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(unique = true)
	private String name;

	@NotNull
	private int maxPlayers;

	@NotNull
	private int currentPlayers;

	@NotNull
	private boolean isActive;

	@ManyToMany
	@JoinTable(
	    name = "user_game",
	    joinColumns = @JoinColumn(name = "game_id"),
	    inverseJoinColumns = @JoinColumn(name = "user_id")
	)
	private List<User> userList;
}
