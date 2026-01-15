package com.shotaroi.librarymanagementsystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbDebugLogger {

    @Bean
    CommandLineRunner logDb(
            @Value("${spring.datasource.url:__MISSING__}") String url,
            @Value("${spring.datasource.username:__MISSING__}") String user,
            @Value("${spring.datasource.password:__MISSING__}") String pass
    ) {
        return args -> {
            System.out.println("=== DB DEBUG ===");
            System.out.println("spring.datasource.url = " + url);
            System.out.println("spring.datasource.username = " + user);
            System.out.println("spring.datasource.password present = " + (!"__MISSING__".equals(pass) && !pass.isBlank()));
            System.out.println("================");
        };
    }
}
