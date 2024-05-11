package com.aplazo.scheme.services;

import com.aplazo.scheme.BaseContainer;
import com.aplazo.scheme.clients.CustomerClient;
import com.aplazo.scheme.dtos.SchemeRequestDTO;
import com.aplazo.scheme.entities.Scheme;
import com.aplazo.scheme.exceptions.ErrorStatusException;
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
    private SchemeService schemeService;
    private SchemeRepository schemeRepository;
    private CustomerClient customerClient;

    @BeforeEach
    public void before() {
        schemeRepository = mock(SchemeRepository.class);
        customerClient = mock(CustomerClient.class);
        schemeService = new SchemeService(schemeRepository, customerClient);
    }

    @Test
    public void create_customerDoesNotExist_exception() {
        var scheme = new Scheme();
        scheme.setRate(10.0);
        scheme.setIdCustomer(100L);
        scheme.setSubTotal(5_000.0);
        scheme.setIsNextPeriod(false);

        when(schemeRepository.save(any())).thenReturn(scheme);

        var schemeRequestDTO = new SchemeRequestDTO();
        schemeRequestDTO.setIdCustomer(100L);
        schemeRequestDTO.setAmount(5_000D);

        var exception = assertThrows(ErrorStatusException.class, () -> schemeService.create(schemeRequestDTO));
        assertEquals("Customer with id 100 does not exist.", exception.getMessage());
    }
}
