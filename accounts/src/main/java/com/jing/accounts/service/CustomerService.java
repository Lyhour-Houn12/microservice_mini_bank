package com.jing.accounts.service;

import com.jing.accounts.payload.dto.CustomerDetailsDto;

public interface CustomerService {
    CustomerDetailsDto getDetailsCustomer(String mobileNumber);
}
