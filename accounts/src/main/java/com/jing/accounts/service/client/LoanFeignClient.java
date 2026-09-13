package com.jing.accounts.service.client;

import com.jing.accounts.payload.dto.LoanDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "loans")
public interface LoanFeignClient {

    @GetMapping("/api/v1/loans/fetch")
    ResponseEntity<LoanDto> fetchLoanDetails(@Valid @RequestParam String mobileNumber);
}
