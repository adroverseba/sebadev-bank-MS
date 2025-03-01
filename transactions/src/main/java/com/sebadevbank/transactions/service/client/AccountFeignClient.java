package com.sebadevbank.transactions.service.client;

import com.sebadevbank.transactions.config.FeignConfig;
import com.sebadevbank.transactions.dto.UpdateBalanceRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "accounts", configuration = FeignConfig.class)
public interface AccountFeignClient {

    @PostMapping("/accounts/update-balance")
    void updateAccountBalance(@RequestBody UpdateBalanceRequest request);

    @GetMapping("/accounts/{accountNumber}/validate-access")
    boolean validateUserAccess(
            @PathVariable Long accountNumber,
            @RequestParam String userId);

}
