package com.example.skillwillproject26.Configurations;

import com.example.skillwillproject26.Filter.CustomAuthenticationFilter;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration

public class Config {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, @Qualifier("customAuth")OncePerRequestFilter customAuthenticaton) throws Exception {
        return http
                .csrf(custom->custom.disable())
                .addFilterBefore(customAuthenticaton, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(x-> x.requestMatchers("/login").permitAll().anyRequest().authenticated())
                .sessionManagement(x->x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }



}
