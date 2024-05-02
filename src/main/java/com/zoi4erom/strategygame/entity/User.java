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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
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

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "email")
	private String email;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "alliance_id")
	private Alliance alliance;

	@OneToOne
	@JoinColumn(name = "statistic_id")
	private Statistic statistic;

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
}
