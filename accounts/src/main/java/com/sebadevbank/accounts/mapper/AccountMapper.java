package com.sebadevbank.accounts.mapper;

import com.sebadevbank.accounts.dto.AccountDto;
import com.sebadevbank.accounts.entity.Accounts;

public class AccountMapper {

    public static AccountDto maptoAccountDto(Accounts account, AccountDto accountDto){
        accountDto.setAccountNumber(account.getId());
        accountDto.setBalance(account.getBalance());
        accountDto.setOwnerName(account.getOwnerName());
        return accountDto;
    }

    public static Accounts mapToAccount(AccountDto accountDto, Accounts account){
        account.setId(accountDto.getAccountNumber());
        account.setBalance(accountDto.getBalance());
        account.setOwnerName(accountDto.getOwnerName());
        return account;
    }
}
