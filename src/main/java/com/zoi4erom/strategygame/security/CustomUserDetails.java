package com.zoi4erom.strategygame.security;

import com.zoi4erom.strategygame.entity.User;
import java.util.Collection;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

	transient User user;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return user.getRoles()
		    .stream()
		    .map(role -> new SimpleGrantedAuthority(role.getName()))
		    .toList();
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
