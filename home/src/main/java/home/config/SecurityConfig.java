package home.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // ===================== 패스워드 인코더 =====================
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ===================== 시큐리티 필터 체인 =====================
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            // CSRF 끔
            .csrf(csrf -> csrf.disable())

            // 접근 권한 설정
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()         	// 누구나 모든 페이지를 볼 수 있도록 설정
            )

            // 로그인/로그아웃 설정
            .formLogin(form -> form
                .loginPage("/login")          
                .defaultSuccessUrl("/", true)		// 로그인 성공 시 홈으로
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")				// 로그아웃시 홈으로
            );

        return http.build();
    }
}