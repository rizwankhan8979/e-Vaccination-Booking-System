package com.example.vaccineManagement.AppSecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(CustomUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    // Authentication Provider
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    // Authentication Manager
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // All Permit Apis(sab dekhna chahen access kar sakte hain)
                        .requestMatchers(new AntPathRequestMatcher("/auth/verify-email")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/auth/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/vaccine/getAll")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/vaccine/get/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/vaccine/doctor/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/doctor/getAll")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/vaccinationCenter/getAll")).permitAll()
                        // All User Access Apis(Only User hi access kar paayega saari apis ko yahan)
                        .requestMatchers(new AntPathRequestMatcher("/user/**")).hasRole("USER")
                        .requestMatchers(new AntPathRequestMatcher("/appointment/book")).hasRole("USER")
                        // All Admin Access Apis(admin access only all apis)
                        .requestMatchers(new AntPathRequestMatcher("/doctor/add")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/doctor/associateWithCenter")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/doctor/**")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/vaccinationCenter/**")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/vaccine/**")).hasRole("ADMIN")
                        .anyRequest().authenticated())
                .cors(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

}
