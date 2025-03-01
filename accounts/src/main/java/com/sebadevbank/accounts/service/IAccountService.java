package com.sebadevbank.accounts.service;

import com.sebadevbank.accounts.dto.AccountDto;
import com.sebadevbank.accounts.dto.TransactionDto;
import com.sebadevbank.accounts.dto.UpdateBalanceRequest;

import java.util.List;

public interface IAccountService {
    List<TransactionDto> getLastTransactions(Long accountNumber);

    List<TransactionDto> getAllTransactions(Long accountNumber);

    void updateBalance(UpdateBalanceRequest request);

    AccountDto getAccountBalance(Long accountNumber);

    Boolean checkAccountExists(Long accountId);

    Boolean validateUserAccess(Long accountNumber, String userId);
}
