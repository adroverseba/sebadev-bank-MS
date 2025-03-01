package com.sebadevbank.transactions.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Todas las solicitudes deben estar autenticadas
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.decoder(jwtDecoder())) // Configura la validación de JWT
                )
                .csrf(csrf -> csrf.disable()); // Deshabilita CSRF (opcional)
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        // Configura el JwtDecoder con la clave pública de Keycloak
        return NimbusJwtDecoder.withJwkSetUri("http://localhost:8180/realms/springboot-keycloak/protocol/openid-connect/certs").build();
    }
}
