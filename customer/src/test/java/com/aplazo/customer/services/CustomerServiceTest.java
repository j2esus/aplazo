package com.aplazo.customer.services;

import com.aplazo.customer.BaseContainer;
import com.aplazo.customer.entities.Customer;
import com.aplazo.customer.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CustomerServiceTest extends BaseContainer {
    private CustomerService customerService;
    private CustomerRepository customerRepository;

    @BeforeEach
    public void before() {
        customerRepository = mock(CustomerRepository.class);
        customerService = new CustomerService(customerRepository);
    }

    @Test
    public void findById_unregisteredId_emptyOptional() {
        when(customerRepository.findById(any())).thenReturn(Optional.empty());

        var customer = customerService.findById(10L);

        assertTrue(customer.isEmpty());
    }

    @Test
    public void findById_registeredId_customer() {
        Optional<Customer> customer = Optional.of(new Customer(1L, "John Wick"));

        when(customerRepository.findById(any())).thenReturn(customer);

        var customerResult = customerService.findById(1L);

        assertTrue(customerResult.isPresent());
        assertEquals(customerResult.get().getId(), customer.get().getId());
        assertEquals(customerResult.get().getName(), customer.get().getName());
    }

    @Test
    public void save_settingTheName_savedCustomer() {
        when(customerRepository.save(any())).thenReturn(new Customer(1L, "John Wick"));

        var customer = new Customer();
        customer.setName("John Wick");

        var result = customerService.save(customer);

        assertEquals(result.getName(), customer.getName());
        assertNotNull(result.getId());
    }

    @Test
    public void save_settingNameAndId_savedCustomerIgnoringTheId() {
        when(customerRepository.save(any())).thenReturn(new Customer(1L, "John Wick"));

        var customer = new Customer();
        customer.setId(2L);
        customer.setName("John Wick");

        var result = customerService.save(customer);

        assertEquals(result.getName(), customer.getName());
        assertEquals(result.getId(), 1L);
        assertNull(customer.getId());
    }

}
