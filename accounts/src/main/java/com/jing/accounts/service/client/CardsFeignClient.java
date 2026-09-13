package com.jing.accounts.service.client;

import com.jing.accounts.payload.dto.CardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cards")
public interface CardsFeignClient {
    @GetMapping("/api/v1/cards/fetch")
    ResponseEntity<CardDto> fetchCardDetails(@RequestParam String mobileNumber);
}
