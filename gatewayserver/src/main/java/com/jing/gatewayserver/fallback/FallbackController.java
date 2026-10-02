package com.jing.gatewayserver.fallback;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @GetMapping("/contact-support")
    public Mono<String> contactSupport() {
        return Mono.just("The service is currently unavailable. Please contact support.");
    }
}
