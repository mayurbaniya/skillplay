package com.skillplay.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests((req) -> req
//                .requestMatchers("/user/auth/", "/skill-play/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**")
//                .permitAll() // Allow these endpoints
//                .anyRequest().authenticated() // Secure other endpoints
//        );
//
//        // Disable CSRF protection (optional, but useful for non-browser clients)
//        http.csrf(AbstractHttpConfigurer::disable);
//
//        return http.build();
//    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests((req)->req.requestMatchers("/user/auth/**", "/v3/api-docs/**","/swagger-ui/**", "/swagger-resources/**")
                .permitAll().anyRequest().authenticated());
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

}
