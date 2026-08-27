package com.jing.accounts.mapper;

import com.jing.accounts.entity.Account;
import com.jing.accounts.payload.dto.AccountDto;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public Account toEntity(AccountDto accountDto) {
        if(accountDto == null) {
            return null;
        }
        Account account = new Account();
        account.setAccountNumber(accountDto.getAccountNumber());
        account.setAccountType(accountDto.getAccountType());
        account.setBranchAddress(accountDto.getBranchAddress());

        return account;
    }

    public AccountDto toDto(Account account) {
        if(account == null) {
            return null;
        }
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setAccountType(account.getAccountType());
        accountDto.setBranchAddress(account.getBranchAddress());
        return accountDto;
    }

}
