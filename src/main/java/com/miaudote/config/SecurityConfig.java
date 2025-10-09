package com.miaudote.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // nichols ficaria orgulhoso cauê

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(CsrfConfigurer::disable) // <-- Desabilita o CSRF aqui
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/animais/cadastrar/*").permitAll()
                .requestMatchers("/usuarios/*").permitAll()
                .requestMatchers("/parceiros/*").permitAll()
                .anyRequest().authenticated()
            );

        return http.build();
    }

}

