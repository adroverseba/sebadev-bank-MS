package com.sebadevbank.accounts.service.client;

import com.sebadevbank.accounts.dto.UserKeycloakDto;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "rest-keycloak-admin")
public interface RestKeycloakAdminClient {

    @GetMapping("/users/{id}")
    UserKeycloakDto getUserById(@PathVariable String id);
}