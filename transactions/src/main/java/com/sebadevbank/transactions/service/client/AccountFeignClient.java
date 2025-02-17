package com.sebadevbank.transactions.service.client;

import com.sebadevbank.transactions.dto.UpdateBalanceRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "accounts")
public interface AccountFeignClient {

    @PostMapping("/accounts/update-balance")
    void updateAccountBalance(@RequestBody UpdateBalanceRequest request);


}
