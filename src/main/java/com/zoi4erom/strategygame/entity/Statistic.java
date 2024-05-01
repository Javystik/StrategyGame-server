package com.zoi4erom.strategygame.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
	private Integer id;

	@Default
	@Column(name = "player_games")
	private Integer playerGames = 0;

	@Default
	@Column(name = "win_games")
	private Integer winGames = 0;

	@Default
	@Column(name = "enemy_units_killed")
	private Integer enemyUnitsKilled = 0;

	@Default
	@Column(name = "units_deaths")
	private Integer unitsDeaths = 0;

	@Default
	@Column(name = "territories_captured")
	private Integer territoriesCaptured = 0;

	@Default
	@Column(name = "territories_lost")
	private Integer territoriesLost = 0;

	@OneToOne(mappedBy = "statistic", cascade = CascadeType.MERGE)
	private User user;
}
