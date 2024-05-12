package com.aplazo.loan.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "SCHEME-CLIENT", url = "http://scheme-app:8082")
public interface SchemeClient {
    @GetMapping("/schemes/{id}")
    SchemeResponse findById(@PathVariable Long id);
}
