package com.zoi4erom.strategygame.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users",
    uniqueConstraints = {@UniqueConstraint(columnNames = "username"),
	  @UniqueConstraint(columnNames = "email")})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 30)
	@NotEmpty
	@Column(name = "username")
	private String username;

	@NotEmpty
	@Column(name = "password")
	private String password;

	@Size(max = 30)
	@Column(name = "email")
	private String email;

	@NotNull
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@ManyToOne
	@JoinColumn(name = "alliance_id")
	private Alliance alliance;

	@NotEmpty
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "statistic_id")
	private Statistic statistic;

	@NotEmpty
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
	    name = "user_role",
	    joinColumns = @JoinColumn(name = "user_id"),
	    inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private Collection<Role> roles;

	@Column(name = "verification_code")
	private String verificationCode;
	private Boolean isVerified;
	private LocalDateTime codeDeathTime;

	@PrePersist
	public void prePersist() {
		if (this.createdAt == null) {
			this.createdAt = LocalDateTime.now();
		}
		if (this.statistic == null) {
			this.statistic = new Statistic();
		}
		if (this.isVerified == null) {
			this.isVerified = false;
		}
	}
}
