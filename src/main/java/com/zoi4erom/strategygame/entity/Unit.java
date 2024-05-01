package com.zoi4erom.strategygame.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
	private Integer id;

	private String name;

	@Column(name = "health_points")
	private int healthPoints;

	private int damage;

	private int speed;

	private int range;

	private int level;

	@OneToOne
	@JoinColumn(name = "unit_type_id")
	private UnitType unitType;
}
