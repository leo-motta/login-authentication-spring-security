package com.leonardomotta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.leonardomotta.repository.UserRepository;
import com.leonardomotta.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return new CustomUserDetailsService(userRepository);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceBean())
        .passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/login", "/process_register", "/img/**", "/js/**", "/css/**","/slick/**").permitAll()
		.anyRequest().authenticated()
        .and()
        .formLogin()
    	.loginPage("/login").permitAll()
    	.defaultSuccessUrl("/success", true)
    	.and()
        .formLogin()
        	.loginPage("/login").permitAll()
        	.defaultSuccessUrl("/success", true)
        .and()
        .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/login").permitAll()
        .and()
        .httpBasic();

		http.csrf().disable();
		http.headers().frameOptions().disable();
			
	}

}
