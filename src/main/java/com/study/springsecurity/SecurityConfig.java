package com.study.springsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
				.formLogin(Customizer.withDefaults())
				.rememberMe(rememberMe -> rememberMe
						.alwaysRemember(true)	// true: 무조건 rememberMe가 체크되게
						.tokenValiditySeconds(3600)// 초 단위
						.userDetailsService(userDetailsService())
						.rememberMeParameter("remember")
						.rememberMeCookieName("remember")
						.key("security")
				);
		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user = User.withUsername("user").password("{noop}1111").roles("USER").build();
		return new InMemoryUserDetailsManager(user);
	}
}
