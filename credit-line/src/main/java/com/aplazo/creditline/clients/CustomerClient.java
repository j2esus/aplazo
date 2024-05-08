package com.aplazo.creditline.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "CUSTOMER-CLIENT", url = "http://localhost:8080")
public interface CustomerClient {
    @GetMapping("/customers/{id}")
    CustomerResponse findById(@PathVariable Long id);
}
