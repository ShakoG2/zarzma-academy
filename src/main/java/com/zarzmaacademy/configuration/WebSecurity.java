package com.zarzmaacademy.configuration;

import com.auth0.spring.security.api.JwtAuthenticationEntryPoint;
import com.zarzmaacademy.Service.UserDetailsServiceImpl;
import io.rocketbase.commons.filter.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired(required = false)
	JwtAuthenticationEntryPoint authenticationEntryPoint;
	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
		authenticationManagerBuilder.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	@Bean
	public JwtTokenFilter jwtTokenFilter(){
		return new JwtTokenFilter();
	}
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.cors().and().csrf().disable()
				.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeRequests().antMatchers("/auth/**").permitAll()
				.antMatchers(HttpMethod.GET, "/user/allusers").permitAll()
				.anyRequest().authenticated();

		httpSecurity.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
