package com.zoi4erom.strategygame.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "statistic")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Statistic {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "player_games")
	private int playerGames;

	@Column(name = "win_games")
	private int winGames;

	@Column(name = "enemy_units_killed")
	private int enemyUnitsKilled;

	@Column(name = "units_deaths")
	private int unitsDeaths;

	@Column(name = "territories_captured")
	private int territoriesCaptured;

	@Column(name = "territories_lost")
	private int territoriesLost;

	@OneToOne(mappedBy = "statistic", cascade = {CascadeType.REMOVE})
	private User user;

	@PrePersist
	public void generateRandomStatisticValues() {
		Random random = new Random();
		this.playerGames = random.nextInt(1001);
		this.winGames = random.nextInt(1001);
		this.enemyUnitsKilled = random.nextInt(1001);
		this.unitsDeaths = random.nextInt(1001);
		this.territoriesCaptured = random.nextInt(1001);
		this.territoriesLost = random.nextInt(1001);
	}
}
