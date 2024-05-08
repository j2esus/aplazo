package com.aplazo.customer.repositories;

import com.aplazo.customer.BaseContainer;
import com.aplazo.customer.entities.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CustomerRepositoryTest extends BaseContainer {

    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    public void before() {
        customerRepository.deleteAll();
    }

    @Test
    public void save_newCustomer_customerWithId() {
        var customer = new Customer();
        customer.setName("John Wick");

        var result = customerRepository.save(customer);

        assertNotNull(result.getId());
        assertEquals(result.getName(), "John Wick");
    }

    @Test
    public void findById_newCustomer_customer() {
        var customer = new Customer();
        customer.setName("John Wick");

        var newCustomer = customerRepository.save(customer);

        var result = customerRepository.findById(newCustomer.getId());

        assertTrue(result.isPresent());
        assertNotNull(result.get().getId());
        assertEquals(result.get().getName(), "John Wick");
    }

    @Test
    public void findById_nonExistentCustomer_emptyOptional() {
        var result = customerRepository.findById(100L);

        assertFalse(result.isPresent());
    }
}
