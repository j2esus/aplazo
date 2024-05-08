package com.aplazo.creditline.services;

import com.aplazo.creditline.BaseContainer;
import com.aplazo.creditline.clients.CustomerClient;
import com.aplazo.creditline.entities.CreditLine;
import com.aplazo.creditline.repositories.CreditLineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CreditLineServiceTest extends BaseContainer {
    private CreditLineService creditLineService;
    private CreditLineRepository creditLineRepository;
    private CustomerClient customerClient;

    @BeforeEach
    public void before() {
        creditLineRepository = mock(CreditLineRepository.class);
        customerClient = mock(CustomerClient.class);
        creditLineService = new CreditLineService(creditLineRepository, customerClient);
    }

    @Test
    public void save_settingAllArguments_savedIgnoringTheArgumentId() {
        when(creditLineRepository.save(any())).thenReturn(new CreditLine(1L, 100L, 50_000.0));

        var creditLine = new CreditLine();
        creditLine.setId(2L);
        creditLine.setIdCustomer(100L);
        creditLine.setAmount(50_000.0);

        var result = creditLineService.save(creditLine);

        assertEquals(creditLine.getAmount(), result.getAmount());
        assertEquals(1L, result.getId());
        assertEquals(50_000.0, result.getAmount());
        assertNull(creditLine.getId());
    }

    @Test
    public void save_settingOnlyRequiredArguments_savedIgnoringTheArgumentId() {
        when(creditLineRepository.save(any())).thenReturn(new CreditLine(1L, 100L, 50_000.0));

        var creditLine = new CreditLine();
        creditLine.setIdCustomer(100L);
        creditLine.setAmount(50_000.0);

        var result = creditLineService.save(creditLine);

        assertEquals(creditLine.getAmount(), result.getAmount());
        assertEquals(1L, result.getId());
        assertEquals(50_000.0, result.getAmount());
    }

}
