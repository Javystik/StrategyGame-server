package com.zoi4erom.strategygame.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String username;
	private String password;
	private String email;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "alliance_id")
	private Alliance alliance;

	@ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
	private List<Role> roles;

	@Column(name = "created_at")
	private LocalDate createdAt;
	@Column(name = "player_games", columnDefinition = "integer default 0")
	private Integer playerGames;
	@Column(name = "win_games")
	private Integer winGames;
	@Column(name = "enemy_units_killed")
	private Integer enemyUnitsKilled;
	@Column(name = "units_deaths")
	private Integer unitsDeaths;
	@Column(name = "territories_captured")
	private Integer territoriesCaptured;
	@Column(name = "territories_lost")
	private Integer territoriesLost;
}
