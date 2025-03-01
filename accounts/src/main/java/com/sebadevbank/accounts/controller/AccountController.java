package com.sebadevbank.accounts.controller;

import com.sebadevbank.accounts.dto.AccountDto;
import com.sebadevbank.accounts.dto.TransactionDto;
import com.sebadevbank.accounts.dto.UpdateBalanceRequest;
import com.sebadevbank.accounts.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final IAccountService accountService;

    @GetMapping("/{ID}")
    public ResponseEntity<AccountDto> getAccountBalance(@PathVariable Long ID){
        return ResponseEntity.ok(accountService.getAccountBalance(ID));
    }

    @GetMapping("/{ID}/activity")
    public ResponseEntity<List<TransactionDto>> getAllTransactions(@PathVariable Long ID){
        return ResponseEntity.ok(accountService.getAllTransactions(ID));
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransactionDto>> getLastTransactions(@PathVariable Long id){
        return ResponseEntity.ok(accountService.getLastTransactions(id));
    }

    @PostMapping("/update-balance")
    public ResponseEntity<Void> updateBalance(@RequestBody UpdateBalanceRequest request){
        accountService.updateBalance(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{accountId}/exists")
    public ResponseEntity<Boolean> checkAccountExists(@PathVariable Long accountId){
        return ResponseEntity.ok(accountService.checkAccountExists(accountId));
    }

    @GetMapping("/{accountNumber}/validate-access")
    public boolean validateUserAccess(
            @PathVariable Long accountNumber,
            @RequestParam String userId) {
        return accountService.validateUserAccess(accountNumber, userId);
    }

}
