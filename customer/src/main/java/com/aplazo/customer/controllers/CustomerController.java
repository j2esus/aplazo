package com.aplazo.customer.controllers;

import com.aplazo.customer.entities.Customer;
import com.aplazo.customer.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/{id}")
    public Customer findById(@PathVariable Long id) {
        return customerService.findById(id).orElseThrow( ()->
            {
                throw new NoSuchElementException("Customer with ID " + id + " does not exist.");
            });
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Customer create(@RequestBody Customer customer) {
        return customerService.save(customer);
    }
}
