package com.zoi4erom.strategygame.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "article")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 120)
	@NotNull
	@Column(unique = true)
	private String name;

	@NotNull
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@NotNull
	private String imageUrl;

	@NotNull
	private String description;

	@NotNull
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "user_id")
	private User user;

	@PrePersist
	public void prePersist() {
		if (this.createdAt == null) {
			this.createdAt = LocalDateTime.now();
		}
	}
}
