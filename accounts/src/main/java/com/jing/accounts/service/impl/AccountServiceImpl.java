package com.jing.accounts.service.impl;

import com.jing.accounts.constant.AccountConstants;
import com.jing.accounts.entity.Account;
import com.jing.accounts.entity.Customer;
import com.jing.accounts.exception.CustomerAlreadyExistsException;
import com.jing.accounts.exception.ResourceNotFoundException;
import com.jing.accounts.mapper.AccountMapper;
import com.jing.accounts.mapper.CustomerMapper;
import com.jing.accounts.payload.dto.AccountDto;
import com.jing.accounts.payload.dto.CustomerDto;
import com.jing.accounts.repository.AccountRepository;
import com.jing.accounts.repository.CustomerRepository;
import com.jing.accounts.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final AccountMapper accountMapper;


    @Override
    @Transactional
    public void createAccount(CustomerDto customerDto) {
        Customer customer = customerMapper.toCustomer(customerDto);
        Optional<Customer> optionalCustomer = customerRepository.findByMobilePhone(customer.getMobilePhone());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already registered with given mobile phone " +  customer.getMobilePhone());
        }
        Customer savedCustomer = customerRepository.save(customer);
        accountRepository.save(createAccount(savedCustomer));
    }

    @Override
    public CustomerDto fetchCustomerDetails(String mobilePhone) {
        Customer customer = customerRepository.findByMobilePhone(mobilePhone)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobilePhone", mobilePhone));

        Account account = accountRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "Customer", customer.getCustomerId().toString()));
        CustomerDto customerDto = customerMapper.toCustomerDto(customer);
        AccountDto accountDto =  accountMapper.toDto(account);
        customerDto.setAccountDto(accountDto);

        return customerDto;
    }

    @Override
    @Transactional
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdate = false;
        AccountDto accountDto = customerDto.getAccountDto();
        if (accountDto != null) {
            Account account = accountRepository.findById(accountDto.getAccountNumber())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Account", "Account Number", accountDto.getAccountNumber().toString()));

            // Actually copy the new values from the DTO onto the managed entity
            account.setAccountType(accountDto.getAccountType());
            account.setBranchAddress(accountDto.getBranchAddress());
            accountRepository.save(account);

            Long customerId = account.getCustomerId();
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Customer", "Customer", customerId.toString()));

            customer.setName(customerDto.getName());
            customer.setEmail(customerDto.getEmail());
            customer.setMobilePhone(customerDto.getMobilePhone());
            customerRepository.save(customer);

            isUpdate = true;
        }
        return isUpdate;
    }


    @Override
    public boolean deleteAccount(String mobilePhone) {
        Customer customer =  customerRepository.findByMobilePhone(mobilePhone)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobilePhone", mobilePhone));
        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

    private Account createAccount(Customer  customer) {
        Account account = new Account();
        account.setCustomerId(customer.getCustomerId());
        account.setAccountNumber(generatedUniqueAccountNumber());
        account.setAccountType(AccountConstants.SAVING);
        account.setBranchAddress(AccountConstants.ADDRESS);
        return account;
    }

    private Long generatedUniqueAccountNumber(){
        long accountNumber;
        do{
            accountNumber = 1000000000L + new Random().nextInt(900000000);
        }while (accountRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }




}
