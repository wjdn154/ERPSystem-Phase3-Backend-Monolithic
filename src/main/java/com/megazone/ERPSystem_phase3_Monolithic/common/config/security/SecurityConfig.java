package com.megazone.ERPSystem_phase3_Monolithic.common.config.security;

import com.megazone.ERPSystem_phase3_Monolithic.hr.repository.basic_information_management.Users.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import java.util.List;

/**
 * JWT 기반 보안 설정을 정의한 클래스임.
 * JwtRequestFilter를 통해 요청을 필터링하고, 사용자 인증을 처리함.
 */
@Configuration
public class SecurityConfig {


    private final JwtRequestFilter jwtRequestFilter;  // JWT 요청 필터 주입 변수 선언
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;  // JWT 인증 진입점 주입 변수 선언

    // JwtRequestFilter 주입하는 생성자
    public SecurityConfig(JwtRequestFilter jwtRequestFilter, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    /**
     * @param http HttpSecurity 객체, 요청에 따른 보안 설정 정의함.
     * @return SecurityFilterChain 보안 필터 체인 반환함.
     * @throws Exception 보안 설정 관련 예외 발생 가능함.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // CSRF 비활성화
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))  // CORS 설정 추가
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/hr/auth/**").permitAll()
                        .requestMatchers("/api/financial/company/**").permitAll()
                        .requestMatchers("/api/notifications/subscribe").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()  // 모든 OPTIONS 요청 허용
                        .anyRequest().authenticated()  // 나머지 모든 요청은 인증이 필요
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // 세션을 사용하지 않고 JWT 인증
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);  // JWT 필터 추가

        return http.build();
    }

    // CORS 설정 빈 추가
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("https://omz-erp.click", "https://www.omz-erp.click", "http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * @param authenticationConfiguration AuthenticationConfiguration 객체, 인증 매니저 설정에 사용됨.
     * @return AuthenticationManager 인증 매니저 반환함.
     * @throws Exception 인증 매니저 설정 중 예외 발생 가능함.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();  // AuthenticationManager 설정하여 반환함.
    }

    /**
     * @return PasswordEncoder 비밀번호 암호화를 위한 BCryptPasswordEncoder 반환함.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // 비밀번호 암호화에 사용할 BCrypt 암호화 방식 설정함.
    }
}