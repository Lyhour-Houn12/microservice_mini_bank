package com.jing.accounts.controller;


import com.jing.accounts.payload.dto.CustomerDetailsDto;
import com.jing.accounts.payload.dto.CustomerDto;
import com.jing.accounts.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDetailsDto> fetchCustomer(@Valid  @RequestParam @Pattern(regexp = "^$|^[0-9]{9}$", message = "Mobile phone must be 10 digits") String mobileNumber) {
        return ResponseEntity.ok(customerService.getDetailsCustomer(mobileNumber));
    }
}
