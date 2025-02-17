package com.sebadevbank.accounts.service.impl;

import com.sebadevbank.accounts.dto.AccountDto;
import com.sebadevbank.accounts.dto.TransactionDto;
import com.sebadevbank.accounts.dto.UpdateBalanceRequest;
import com.sebadevbank.accounts.entity.Accounts;
import com.sebadevbank.accounts.mapper.AccountMapper;
import com.sebadevbank.accounts.repository.AccountRepository;
import com.sebadevbank.accounts.service.IAccountService;
import com.sebadevbank.accounts.service.client.TransactionFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private final AccountRepository accountRepository;
    private final TransactionFeignClient transactionFeignClient;

    @Override
    public List<TransactionDto> getLastTransactions(Long accountNumber) {
        return transactionFeignClient.getLastTransactions(accountNumber);
    }

    @Override
    public void updateBalance(UpdateBalanceRequest request) {
        //buscar cuenta en la db
        Accounts account = accountRepository.findById(request.getAccountId())
                .orElseThrow(()->new RuntimeException("Cuenta no encontrada"));

        //actualizar el balance segun transaccion
        BigDecimal newBalance = account.getBalance();
        BigDecimal amount = request.getAmount();

        if("CREDIT".equalsIgnoreCase(request.getTransactionType())){
            newBalance = newBalance.add(amount);//suma para credito
        }else if("DEBIT".equalsIgnoreCase(request.getTransactionType())){
            if (newBalance.compareTo(amount)<0){  // SI newBalance es menor que amount
                throw new IllegalArgumentException("Saldo insuficiente para realizar esta transaccion.");
            }
            newBalance = newBalance.subtract(amount);
        }

        //guardo nuevo balance
        account.setBalance(newBalance);
        accountRepository.save(account);
    }

    @Override
    public AccountDto getAccountBalance(Long accountNumber) {
        //buscar cuenta en la db
        Accounts account = accountRepository.findById(accountNumber)
                .orElseThrow(()->new RuntimeException("Cuenta no encontrada"));

        //retornar el saldo de la cuenta
        return AccountMapper.maptoAccountDto(account,new AccountDto());
    }


}
