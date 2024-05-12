package com.aplazo.loan.services;

import com.aplazo.loan.BaseContainer;
import com.aplazo.loan.clients.CustomerClient;
import com.aplazo.loan.clients.CustomerResponse;
import com.aplazo.loan.clients.SchemeClient;
import com.aplazo.loan.dtos.LoanRequestDTO;
import com.aplazo.loan.entities.Loan;
import com.aplazo.loan.exceptions.ErrorStatusException;
import com.aplazo.loan.repositories.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class LoanServiceTest extends BaseContainer {
    private LoanService schemeService;
    private LoanRepository schemeRepository;
    private CustomerClient customerClient;
    private SchemeClient schemeClient;

    @BeforeEach
    public void before() {
        schemeRepository = mock(LoanRepository.class);
        customerClient = mock(CustomerClient.class);
        schemeClient = mock(SchemeClient.class);
        schemeService = new LoanService(schemeRepository, customerClient, schemeClient);
    }

    @Test
    public void create_customerDoesNotExist_exception() {
        var scheme = new Loan();
        scheme.setRate(10.0);
        scheme.setIdCustomer(100L);
        scheme.setSubTotal(5_000.0);
        scheme.setIsNextPeriod(false);

        when(schemeRepository.save(any())).thenReturn(scheme);

        var schemeRequestDTO = new LoanRequestDTO();
        schemeRequestDTO.setIdCustomer(100L);
        schemeRequestDTO.setAmount(5_000D);

        var exception = assertThrows(ErrorStatusException.class, () -> schemeService.create(schemeRequestDTO));
        assertEquals("Customer with id 100 does not exist.", exception.getMessage());
    }

    @Test
    public void create_schemeDoesNotExist_exception() {
        var scheme = new Loan();
        scheme.setRate(10.0);
        scheme.setIdCustomer(100L);
        scheme.setSubTotal(5_000.0);
        scheme.setIsNextPeriod(false);

        when(schemeRepository.save(any())).thenReturn(scheme);
        when(customerClient.findById(any())).thenReturn(new CustomerResponse());

        var schemeRequestDTO = new LoanRequestDTO();
        schemeRequestDTO.setIdCustomer(100L);
        schemeRequestDTO.setAmount(5_000D);
        schemeRequestDTO.setIdScheme(150L);

        var exception = assertThrows(ErrorStatusException.class, () -> schemeService.create(schemeRequestDTO));
        assertEquals("Scheme with id 150 does not exist.", exception.getMessage());
    }
}
