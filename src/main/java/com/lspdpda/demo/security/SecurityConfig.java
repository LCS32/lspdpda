package com.lspdpda.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.lspdpda.demo.config.CustomAccessDeniedHandler;
import com.lspdpda.demo.security.service.UserDetailsServiceImpl;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;

	@Bean
	PasswordEncoder passwordEnconder() {
		return new BCryptPasswordEncoder();

	}

	@Bean
	AccessDeniedHandler accessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEnconder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/index", "/home", "/forbidden", "/login", "/registro").permitAll()
				.antMatchers("/registro").permitAll().antMatchers("/main/**").permitAll().antMatchers("/resources/**")
				.permitAll().antMatchers("/static/**").permitAll().antMatchers("/css/**").permitAll()
				.antMatchers("/images/**").permitAll().antMatchers("/js/**").permitAll().antMatchers("/templates/**")
				.permitAll().antMatchers("/error/**").permitAll().antMatchers("/plantilla/**").permitAll()
				.antMatchers("/usuario/registro").permitAll().antMatchers("/usuario/registrar").permitAll().anyRequest()
				.authenticated().and().formLogin().loginProcessingUrl("/signin").loginPage("/login").permitAll()
				.defaultSuccessUrl("/").usernameParameter("usuario").passwordParameter("password").and()
				.exceptionHandling().accessDeniedHandler(accessDeniedHandler()).and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout")
				.permitAll().deleteCookies("JSESSIONID").and().rememberMe().tokenValiditySeconds(3600000).key("secret")
				.rememberMeParameter("checkRememberMe");
	}
}
