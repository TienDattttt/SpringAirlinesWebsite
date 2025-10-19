package com.example.SwipeFlight.auth;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity // Enable Spring Security’s web security
public class WebSecurityConfig {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) {
		try {
			http

					.authorizeHttpRequests((requests) -> requests

							.requestMatchers("/admin**").hasAuthority("ADMIN")

							.requestMatchers("/booking**").authenticated()
							.requestMatchers("/user**").hasAuthority("USER")

							.anyRequest().permitAll())

					.formLogin(formLogin -> formLogin
							.loginPage("/login"))

					.exceptionHandling(exceptionHandling -> exceptionHandling
							.accessDeniedHandler(accessDeniedHandler()));
			return http.build();
		} catch (Exception e) {
			System.err.println("*** Error -- WebSecurityConfig -- security configuration error: " + e.getMessage());
			return null;
		}
	}

	@Bean
	AccessDeniedHandler accessDeniedHandler() {
		return new AccessDeniedHandler() {

			@Override
			public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) {
				try {

					response.sendRedirect(request.getContextPath() + "/accessDenied");
				} catch (IOException error) {
					System.err.println("\n*** Error ***\nClass: WebSecurityConfig "
							+ "\nDetails: " + e.getMessage());
				}
			}
		};
	}
}
