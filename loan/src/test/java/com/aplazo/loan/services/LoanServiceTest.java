package com.aplazo.loan.services;

import com.aplazo.loan.BaseContainer;
import com.aplazo.loan.clients.CustomerClient;
import com.aplazo.loan.clients.CustomerResponse;
import com.aplazo.loan.clients.SchemeClient;
import com.aplazo.loan.clients.SchemeResponse;
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
    private LoanRepository loanRepository;
    private CustomerClient customerClient;
    private SchemeClient schemeClient;

    @BeforeEach
    public void before() {
        loanRepository = mock(LoanRepository.class);
        customerClient = mock(CustomerClient.class);
        schemeClient = mock(SchemeClient.class);
        schemeService = new LoanService(loanRepository, customerClient, schemeClient);
    }

    @Test
    public void create_customerDoesNotExist_exception() {
        var loan = new Loan();
        loan.setRate(10.0);
        loan.setIdCustomer(100L);
        loan.setSubTotal(5_000.0);
        loan.setIsNextPeriod(false);

        when(loanRepository.save(any())).thenReturn(loan);

        var loanRequestDTO = new LoanRequestDTO();
        loanRequestDTO.setIdCustomer(100L);
        loanRequestDTO.setAmount(5_000D);

        var exception = assertThrows(ErrorStatusException.class, () -> schemeService.create(loanRequestDTO));
        assertEquals("Customer with id 100 does not exist.", exception.getMessage());
    }

    @Test
    public void create_schemeDoesNotExist_exception() {
        var loan = new Loan();
        loan.setRate(10.0);
        loan.setIdCustomer(100L);
        loan.setSubTotal(5_000.0);
        loan.setIsNextPeriod(false);

        when(loanRepository.save(any())).thenReturn(loan);
        when(customerClient.findById(any())).thenReturn(new CustomerResponse());

        var loanRequestDTO = new LoanRequestDTO();
        loanRequestDTO.setIdCustomer(100L);
        loanRequestDTO.setAmount(5_000D);
        loanRequestDTO.setIdScheme(150L);

        var exception = assertThrows(ErrorStatusException.class, () -> schemeService.create(loanRequestDTO));
        assertEquals("Scheme with id 150 does not exist.", exception.getMessage());
    }

    @Test
    public void create_schemeAndArgumentCustomerIdDoesNotEqual_exception() {
        var loan = new Loan();
        loan.setRate(10.0);
        loan.setIdCustomer(100L);
        loan.setSubTotal(5_000.0);
        loan.setIsNextPeriod(false);

        when(loanRepository.save(any())).thenReturn(loan);
        when(customerClient.findById(any())).thenReturn(new CustomerResponse());

        var scheme = new SchemeResponse();
        scheme.setId(1L);
        scheme.setIdCustomer(101L);
        scheme.setRate(10.0);
        scheme.setSubTotal(5_000.0);
        when(schemeClient.findById(any())).thenReturn(scheme);

        var loanRequestDTO = new LoanRequestDTO();
        loanRequestDTO.setIdCustomer(100L);
        loanRequestDTO.setAmount(5_000D);
        loanRequestDTO.setIdScheme(150L);

        var exception = assertThrows(ErrorStatusException.class, () -> schemeService.create(loanRequestDTO));
        assertEquals("The customer id doest not equal with customer id from the scheme.", exception.getMessage());

    }
}
