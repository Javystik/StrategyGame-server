package com.zoi4erom.strategygame.security;

import com.zoi4erom.strategygame.utils.JwtTokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filter to intercept and process JWT authentication token in the HTTP request.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

	private final JwtTokenUtils jwtTokenUtils;

	/**
	 * This method intercepts incoming HTTP requests, extracts JWT tokens from the Authorization
	 * header, validates them, and sets up the authentication context based on the extracted
	 * information. If the token is valid and the authentication context is not already set, it
	 * creates an authentication token and sets it in the security context.
	 *
	 * @param request     HTTP servlet request
	 * @param response    HTTP servlet response
	 * @param filterChain Filter chain for handling the request
	 * @throws ServletException if an exception occurs that interrupts the filter chain
	 * @throws IOException      if an I/O exception occurs
	 */
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
				log.info("Jwt Token is Expired");
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