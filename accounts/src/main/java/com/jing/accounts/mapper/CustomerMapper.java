package com.jing.accounts.mapper;

import com.jing.accounts.entity.Account;
import com.jing.accounts.entity.Customer;
import com.jing.accounts.payload.dto.CustomerDto;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toCustomer(CustomerDto customerDto) {
        if (customerDto == null) {
            return null;
        }

        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobilePhone(customerDto.getMobilePhone());

        return customer;
    }
    public CustomerDto toCustomerDto(Customer customer) {
        if (customer == null) {
            return null;
        }
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobilePhone(customer.getMobilePhone());
        return customerDto;
    }
}
