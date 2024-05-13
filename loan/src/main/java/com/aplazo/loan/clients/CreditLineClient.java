package com.aplazo.loan.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "CREDIT-LINE-CLIENT", url = "http://credit-line-app:8081")
public interface CreditLineClient {
    @GetMapping("/credit-line/customer/{id}")
    CreditLineResponse findByIdCustomer(@PathVariable Long id);
}
