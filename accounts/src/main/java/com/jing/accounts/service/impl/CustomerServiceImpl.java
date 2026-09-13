package com.jing.accounts.service.impl;

import com.jing.accounts.entity.Account;
import com.jing.accounts.entity.Customer;
import com.jing.accounts.exception.ResourceNotFoundException;
import com.jing.accounts.mapper.AccountMapper;
import com.jing.accounts.mapper.CustomerMapper;
import com.jing.accounts.payload.dto.AccountDto;
import com.jing.accounts.payload.dto.CardDto;
import com.jing.accounts.payload.dto.CustomerDetailsDto;
import com.jing.accounts.payload.dto.LoanDto;
import com.jing.accounts.repository.AccountRepository;
import com.jing.accounts.repository.CustomerRepository;
import com.jing.accounts.service.CustomerService;
import com.jing.accounts.service.client.CardsFeignClient;
import com.jing.accounts.service.client.LoanFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final CardsFeignClient cardsFeignClient;
    private final LoanFeignClient loanFeignClient;
    private final CustomerMapper customerMapper;
    private final AccountMapper accountMapper;

    @Override
    public CustomerDetailsDto getDetailsCustomer(String mobileNumber) {
        Customer customer = customerRepository.findByMobilePhone(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "Mobile Number", mobileNumber));

        Account account = accountRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "Customer", customer.getCustomerId().toString()));

        AccountDto accountDto = accountMapper.toDto(account);

        ResponseEntity<CardDto> responseCards = cardsFeignClient.fetchCardDetails(mobileNumber);
        ResponseEntity<LoanDto> responseLoan = loanFeignClient.fetchLoanDetails(mobileNumber);

        CustomerDetailsDto customerDetailsDto = customerMapper.customerDerailsDto(customer);
        customerDetailsDto.setAccountDto(accountDto);
        customerDetailsDto.setCardDto(responseCards.getBody());
        customerDetailsDto.setLoanDto(responseLoan.getBody());

        return customerDetailsDto;
    }
}
