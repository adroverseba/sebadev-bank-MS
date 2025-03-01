package com.sebadevbank.accounts.service.client;

import com.sebadevbank.accounts.dto.TransactionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "transactions")
public interface TransactionFeignClient {

    @GetMapping("/transactions/{accountId}/last")
    List<TransactionDto> getLastTransactions(@PathVariable Long accountId);

    @GetMapping("/transactions/{accountNumber}/all")
    List<TransactionDto> getAllTransactions(@PathVariable Long accountNumber);

}
