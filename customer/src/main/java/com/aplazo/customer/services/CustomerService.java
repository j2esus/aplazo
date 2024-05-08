package com.aplazo.customer.services;

import com.aplazo.customer.entities.Customer;
import com.aplazo.customer.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer save(Customer customer){
        customer.setId(null);
        return customerRepository.save(customer);
    }
}
