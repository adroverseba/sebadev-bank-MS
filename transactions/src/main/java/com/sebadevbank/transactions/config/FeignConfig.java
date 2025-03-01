package com.sebadevbank.transactions.config;

import com.sebadevbank.transactions.exception.CustomFeignErrorDecoder;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;

public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            // Obtiene el token JWT del contexto de seguridad
            String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
            requestTemplate.header("Authorization", "Bearer " + token);//incluye a cada request el token
        };
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomFeignErrorDecoder();
    }
}