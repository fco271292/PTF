package com.fco271292.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

import com.fco271292.services.UserDetailService

@Configuration
@EnableWebSecurity
class WebSecutiryConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailService userDetailService
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		new BCryptPasswordEncoder()
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.userDetailsService(userDetailService)
		.passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/login/**").permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				//.loginPage("/login")
				.defaultSuccessUrl("/")
				.permitAll()
			.and()
			
			.logout()
				.permitAll()
			
			.and()
				.httpBasic()
	}

}
