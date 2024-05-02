package com.zoi4erom.strategygame.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
//TODO поміняти версію jwt токена на більш нову = поміняти методи на нові
public class JwtTokenUtils {

	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.lifetime}")
	private Duration jwtLifetime;

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		var rolesList = userDetails.getAuthorities().stream()
		    .map(GrantedAuthority::getAuthority)
		    .toList();
		claims.put("roles", rolesList);

		LocalDateTime issuedDateTime = LocalDateTime.now();
		LocalDateTime expiredDateTime = issuedDateTime.plus(jwtLifetime);

		SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

		return Jwts.builder()
		    .setClaims(claims)
		    .setSubject(userDetails.getUsername())
		    .setIssuedAt(java.sql.Timestamp.valueOf(issuedDateTime))
		    .setExpiration(java.sql.Timestamp.valueOf(expiredDateTime))
		    .signWith(key)
		    .compact();
	}

	public String getUsername(String token) {
		return getAllClaimsFromToken(token).getSubject();
	}

	@SuppressWarnings("unchecked")
	public List<String> getRoles(String token) {
		return getAllClaimsFromToken(token).get("roles", List.class);
	}

	private Claims getAllClaimsFromToken(String token) {
		SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
		return Jwts.parserBuilder()
		    .setSigningKey(key)
		    .build()
		    .parseClaimsJws(token)
		    .getBody();
	}
}
