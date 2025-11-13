package com.koreait.project.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	@Bean 
	CorsConfigurationSource corsConfigurationSource() {
		  CorsConfiguration config = new CorsConfiguration();
			config.setAllowedOrigins(List.of("http://localhost:3000", "http://192.168.1.9:3000"));
			config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
			config.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));
			config.setAllowCredentials(true);
			config.setExposedHeaders(List.of("Set-Cookie"));
			
			UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
			source.registerCorsConfiguration("/**", config);
			
			return source;
	}
	
	 @Bean
	 SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		 http
		     .authorizeHttpRequests(authorize -> authorize
		    		 .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
		             .requestMatchers("/**").permitAll())
                     .cors(Customizer.withDefaults())
		             .csrf(csrf -> csrf.disable())
		             .exceptionHandling(ex -> ex.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
		             .formLogin(formLogin -> formLogin
		            	.loginProcessingUrl("/api/login")
		            	.usernameParameter("username")
		            	.passwordParameter("password")
		            	.successHandler((req, res, auth) -> res.setStatus(HttpStatus.OK.value()))
		            	.failureHandler((req, res, ex) -> res.setStatus(HttpStatus.UNAUTHORIZED.value())))
		             .logout(logout -> logout.logoutUrl("/api/logout")
		            		 .logoutSuccessHandler((req, res, auth) -> res.setStatus(HttpStatus.OK.value()))
		             );
		             
		 return http.build();
	 }
	 
	 // passwordEncoder 암호화 시켜서 비밀번호 저장 
	 @Bean 
	 PasswordEncoder passwordEncoder() {
		 return new BCryptPasswordEncoder();
	 }
	 
	// authenticationManager 직접 쓸 일은 없음. 프레임워크 내부에서 사용. 
	 @Bean
	 AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		 return authenticationConfiguration.getAuthenticationManager(); 
	 }

}
