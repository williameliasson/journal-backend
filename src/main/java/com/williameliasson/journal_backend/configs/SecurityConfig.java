package com.williameliasson.journal_backend.configs;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.williameliasson.journal_backend.services.CustomUserDetailsService;

// src: https://spring.io/guides/gs/securing-web

@Configuration 
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;

    SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean 
    SecurityFilterChain securityFilterChain(HttpSecurity http){
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> {})
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/api/login", "/api/register", "/api/logout").permitAll()
                .anyRequest().authenticated())
            .logout(logout -> logout
                .logoutUrl("/api/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            )
            .userDetailsService(customUserDetailsService);

        return http.build();
    }

    @Bean 
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    configuration.setAllowedOrigins(List.of("http://localhost:4200"));
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source =
        new UrlBasedCorsConfigurationSource();

    source.registerCorsConfiguration("/**", configuration);

    return source;
}
}
