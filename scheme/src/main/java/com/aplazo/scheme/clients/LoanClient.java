package com.aplazo.scheme.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "LOAN-CLIENT", url = "http://loan-app:8083")
public interface LoanClient {
    @GetMapping("/loans/available-credit/customer/{id}")
    Long findById(@PathVariable Long id);
}
