package com.jing.accounts.service;

import com.jing.accounts.payload.dto.CustomerDto;

public interface AccountService {
    void createAccount(CustomerDto customerDto);

    CustomerDto fetchCustomerDetails(String mobilePhone);

    boolean updateAccount(CustomerDto customerDto);

    boolean deleteAccount(String mobilePhone);
}
