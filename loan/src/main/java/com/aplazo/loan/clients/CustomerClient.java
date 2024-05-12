package com.aplazo.loan.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "CUSTOMER-CLIENT", url = "http://customer-app:8080")
public interface CustomerClient {
    @GetMapping("/customers/{id}")
    CustomerResponse findById(@PathVariable Long id);
}
