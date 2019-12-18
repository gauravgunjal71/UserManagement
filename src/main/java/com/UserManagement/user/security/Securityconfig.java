package com.UserManagement.user.security;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.google.gson.Gson;

@Configuration
@EnableWebSecurity
public class Securityconfig extends WebSecurityConfigurerAdapter {

	@Bean
	public Jwtauthfilter jwtAuthenticationFilter() {
		return new Jwtauthfilter();
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS, "/**")
				.permitAll()
//				.antMatchers("/v2/api-docs", "/configuration/ui",
//						"/swagger-resources/**", "/configuration/**",
//						"/swagger-ui.html", "/webjars/**")
//				.permitAll()
				.antMatchers(HttpMethod.POST, "/login")
				.permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.csrf()
				.disable()
				.addFilterBefore(jwtAuthenticationFilter(),
						UsernamePasswordAuthenticationFilter.class);
	}

}
