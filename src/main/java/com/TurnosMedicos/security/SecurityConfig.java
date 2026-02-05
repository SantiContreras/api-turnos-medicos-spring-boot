package com.TurnosMedicos.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;



import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
public class SecurityConfig {

    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/turnosMedicos/**").hasAnyRole("USER", "ADMIN")
            .requestMatchers("/api/turnosMedicos/**").hasRole("ADMIN")
            .anyRequest().authenticated()
        )
        .httpBasic(withDefaults());
        return http.build();
    }
    
    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails admin = User
                .withUsername("admin")
                .password(passwordEncoder().encode("1234"))
                .roles("ADMIN")
                .build();

       
        
        UserDetails user = User
        		.withUsername("user")
        	    .password(passwordEncoder().encode("1234"))
        		.roles("USER")
        		.build();
        
        return new InMemoryUserDetailsManager(user , admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}