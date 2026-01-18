package com.shotaroi.librarymanagementsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .logout(logout -> logout.disable())

                // simplest: Basic Auth
                .httpBasic(basic -> {})

                .authorizeHttpRequests(auth -> auth
                        // public reads
                        .requestMatchers(HttpMethod.GET, "/api/books/**").permitAll()

                        // protect writes
                        .requestMatchers(HttpMethod.POST, "/api/books/**").authenticated()
                        .requestMatchers(HttpMethod.PUT,  "/api/books/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE,"/api/books/**").authenticated()

                        // everything else
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}
