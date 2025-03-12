package com.study.springsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
				.formLogin(form -> form
						.loginPage("/loginPage")
						.loginPage("/loginProc")
						.defaultSuccessUrl("/", true)
						.failureUrl("/failed")
						.usernameParameter("userId")
						.passwordParameter("passwd")

						// 얘네가 defaultSuccessUrl 이거보다 더 우선시 됨.
						.successHandler((request, response, authentication) -> {
							System.out.println("authentication : " + authentication);
							response.sendRedirect("/home");
						})
						.failureHandler((request, response, exception) -> {
							System.out.println("exception : " + exception);
							response.sendRedirect("/loginPage");
						})
						.permitAll()
				);

		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user = User.withUsername("name").password("{noop}1111").roles("USER").build();
		return new InMemoryUserDetailsManager(user);
	}
}
