package com.sebadevbank.cards.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "accounts")
public interface AccountsFeignClient {

    @GetMapping("/accounts/{accountId}/exists")
    public ResponseEntity<Boolean> checkAccountExists(@PathVariable Long accountId);
}
