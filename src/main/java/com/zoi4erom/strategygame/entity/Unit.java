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
@Table(name = "unit")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Unit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private int currentHealthPoints;

	@NotNull
	private boolean isAlive;

	@NotNull
	private int x;

	@NotNull
	private int y;

	@ManyToOne
	@JoinColumn(name = "game_id")
	@NotNull
	private Game game;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@NotNull
	private User user;

	@ManyToOne
	@JoinColumn(name = "unit_template_id")
	@NotNull
	private UnitTemplate unitTemplate;
}
