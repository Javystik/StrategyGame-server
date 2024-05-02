package com.zoi4erom.strategygame.security;

import com.zoi4erom.strategygame.exception.JwtExpiredException;
import com.zoi4erom.strategygame.utils.JwtTokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

	private final JwtTokenUtils jwtTokenUtils;

	@Override
	protected void doFilterInternal(
	    @NonNull HttpServletRequest request,
	    @NonNull HttpServletResponse response,
	    @NonNull FilterChain filterChain) throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");
		String username = null;
		String jwt = null;
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			jwt = authHeader.substring(7);
			try {
				username = jwtTokenUtils.getUsername(jwt);
			} catch (ExpiredJwtException e) {
				throw new JwtExpiredException("JWT Token Expired");
			}
		}
		if (username != null
		    && SecurityContextHolder.getContext().getAuthentication() == null) {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
			    username,
			    null,
			    jwtTokenUtils.getRoles(jwt).stream().map(SimpleGrantedAuthority::new).toList()
			);
			SecurityContextHolder.getContext().setAuthentication(token);
		}
		filterChain.doFilter(request, response);
	}
}