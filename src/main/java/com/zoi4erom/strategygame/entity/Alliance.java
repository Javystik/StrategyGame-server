package com.zoi4erom.strategygame.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "alliance")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Alliance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(max = 30)
	private String name;

	@NotNull
	@Column(name = "members_count")
	private Integer membersCount;

	@NotNull
	@Column(name = "total_wins")
	private Integer totalWins;

	@OneToMany(mappedBy = "alliance", cascade = CascadeType.MERGE)
	private List<User> users;
}
