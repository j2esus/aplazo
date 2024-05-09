package com.aplazo.scheme.services;

import com.aplazo.scheme.BaseContainer;
import com.aplazo.scheme.clients.CustomerClient;
import com.aplazo.scheme.clients.CustomerResponse;
import com.aplazo.scheme.entities.Scheme;
import com.aplazo.scheme.repositories.SchemeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SchemeServiceTest extends BaseContainer {
    private SchemeService creditLineService;
    private SchemeRepository creditLineRepository;
    private CustomerClient customerClient;

    /*
    @BeforeEach
    public void before() {
        creditLineRepository = mock(SchemeRepository.class);
        customerClient = mock(CustomerClient.class);
        creditLineService = new SchemeService(creditLineRepository, customerClient);
    }

    @Test
    public void save_customerDoesNotExist_errorStatus() {
        when(creditLineRepository.save(any())).thenReturn(new Scheme(1L, 100L, 50_000.0));

        var creditLine = new Scheme();
        creditLine.setId(2L);
        creditLine.setIdCustomer(100L);
        creditLine.setAmount(50_000.0);

        var result = creditLineService.save(creditLine);

        assertEquals(result.get("status"), "FAILURE");
    }

    @Test
    public void save_customerExists_successStatus() {
        when(creditLineRepository.save(any())).thenReturn(new Scheme(1L, 100L, 50_000.0));
        when(customerClient.findById(any())).thenReturn(new CustomerResponse(100L, "John Wick"));

        var creditLine = new Scheme();
        creditLine.setIdCustomer(100L);
        creditLine.setAmount(50_000.0);

        var result = creditLineService.save(creditLine);

        assertEquals(result.get("status"), "SUCCESS");
    }

     */

}
