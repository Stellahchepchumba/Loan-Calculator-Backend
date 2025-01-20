package com.LoanCalculator.LoanCalculator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Set up CORS configuration
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/api/loans/calculate", "/api/loans/export").permitAll() // Allow these endpoints
                        .anyRequest().authenticated() // Authenticate all other requests
                )
                .httpBasic(httpBasic -> {}); // Modern approach for HTTP Basic Auth

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
                "http://localhost:3000", // Localhost for development
                "https://loan-calculator-3opx2xvt5-stellahs-projects.vercel.app", // Frontend hosted on Vercel
                "https://loan-calculator-backend-8q48.onrender.com" // Your backend Render URL
        ));
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setExposedHeaders(List.of("Authorization", "Content-Disposition"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            UserDetails user = User.withUsername("user")
                    .password(passwordEncoder().encode("password"))
                    .roles("USER")
                    .build();
            return user;
        };
    }
}
