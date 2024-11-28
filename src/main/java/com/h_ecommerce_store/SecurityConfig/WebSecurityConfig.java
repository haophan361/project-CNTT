package com.h_ecommerce_store.SecurityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http
                .csrf(csrf->csrf.ignoringRequestMatchers(
                        "/user/addToCart/**"
                        , "/user/updateCart/**"
                        ,"/user/deleteCart/**"
                        ,"/user/updateStatus/**"
                        ,"/staff/deleteBill/**"
                        ,"/admin/changeRole/**"))
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(
                                "/",
                                "/login",
                                "/web/**",
                                "/js/**",
                                "/css/**",
                                "/fonts/**",
                                "/img/**",
                                "/screenshot/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/staff/**").hasRole("STAFF")
                        .requestMatchers("/user/**").hasRole( "USER")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll());
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception
    {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
