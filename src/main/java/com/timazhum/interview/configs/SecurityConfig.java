package com.timazhum.interview.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {
    @Bean
    @Scope("prototype")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
