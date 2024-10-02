package com.h_ecommerce_store.SecurityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig
{
    @Bean
    public SecurityFilterChain  securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/",
                                "/home",
                                "/loginForm",
                                "/registerForm",
                                "/login",
                                "/register",
                                "/js/**",
                                "/css/**",
                                "/fonts/**",
                                "/img/**",
                                "/screenshot/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/loginForm")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/home")
                        .permitAll());
        return http.build();
    }
}
