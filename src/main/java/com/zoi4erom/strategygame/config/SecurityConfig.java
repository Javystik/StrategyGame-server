package com.zoi4erom.strategygame.config;

import com.zoi4erom.strategygame.security.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for setting up security filters and defining security rules for the
 * application.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtRequestFilter jwtRequestFilter;

	/**
	 * Configures the security filter chain for the application.
	 *
	 * @param http The HttpSecurity object to configure security settings.
	 * @return A SecurityFilterChain object representing the configured security filter chain.
	 * @throws Exception If an error occurs while configuring security settings.
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(AbstractHttpConfigurer::disable)
		    .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
		    .authorizeHttpRequests(
			  auth -> auth
				.requestMatchers("/auth/login", "/auth/register", "/web-socket/**").permitAll()
				.anyRequest().authenticated()
		    )
		    .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
			  SessionCreationPolicy.STATELESS))
		    .build();
	}

	/**
	 * Provides a PasswordEncoder bean for encoding passwords.
	 *
	 * @return A PasswordEncoder bean instance.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
