package com.jing.accounts.controller;


import com.jing.accounts.payload.dto.CustomerDetailsDto;
import com.jing.accounts.payload.dto.CustomerDto;
import com.jing.accounts.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDetailsDto> fetchCustomer(@RequestHeader("eazybank-correlation-id") String correlationId, @Valid  @RequestParam @Pattern(regexp = "^$|^[0-9]{9}$", message = "Mobile phone must be 10 digits") String mobileNumber) {
        logger.debug("eazybank-correlation-id found {}", correlationId);
        return ResponseEntity.ok(customerService.getDetailsCustomer(mobileNumber, correlationId));
    }
}
