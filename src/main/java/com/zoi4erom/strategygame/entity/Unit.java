package com.zoi4erom.strategygame.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
	@Size(max = 30)
	private String name;

	@NotNull
	@Column(name = "health_points")
	private int healthPoints;

	@NotNull
	private int damage;

	@NotNull
	private int speed;

	@NotNull
	private int range;

	@NotNull
	private int level;

	@ManyToOne
	@JoinColumn(name = "unit_type_id")
	private UnitType unitType;
}
