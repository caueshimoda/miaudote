package com.miaudote.config;

import com.miaudote.jwt.AuthEntryPointJwt;
import com.miaudote.jwt.AuthTokenFilter;
import com.miaudote.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    // Isso tava causando um ciclo de dependência
    //@Autowired
    //UsuarioService usuarioService;


    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }


    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF
                .cors(Customizer.withDefaults()) // Disable CORS (or configure if needed)
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(unauthorizedHandler)
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/usuarios/login", "/img/Miaudotefinal1.png", "/parceiros/cadastrar", "/adotantes/cadastrar", "/health").permitAll() 
                                .anyRequest().authenticated()
   
                );

        // Add the JWT Token filter before the UsernamePasswordAuthenticationFilter
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    /*
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(CsrfConfigurer::disable) // <-- Desabilita o CSRF aqui
            .authorizeHttpRequests(authz -> authz
                /* 
                .requestMatchers("/animais/*").permitAll()
                .requestMatchers("/usuarios/*").permitAll()
                .requestMatchers("/parceiros/*").permitAll()
                .requestMatchers("/adotantes/*").permitAll()
                .requestMatchers("/adocoes/*").permitAll()
                .requestMatchers("/favoritos/*").permitAll()
                .requestMatchers("/ceps/*").permitAll()
                //.anyRequest().authenticated()
                .anyRequest().permitAll()
            );

        return http.build();
    }
    */

}

