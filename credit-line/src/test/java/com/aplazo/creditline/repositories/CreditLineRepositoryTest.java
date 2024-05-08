package com.aplazo.creditline.repositories;

import com.aplazo.creditline.BaseContainer;
import com.aplazo.creditline.entities.CreditLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CreditLineRepositoryTest extends BaseContainer {

    @Autowired
    CreditLineRepository creditLineRepository;

    @BeforeEach
    public void before() {
        creditLineRepository.deleteAll();
    }

    @Test
    public void save_newCreditLine_creditLineWithId() {
        var creditLine = new CreditLine();
        creditLine.setAmount(50_000.0);
        creditLine.setIdCustomer(100L);

        var result = creditLineRepository.save(creditLine);

        assertNotNull(result.getId());
        assertEquals(50_000.0, result.getAmount());
        assertEquals(100L, result.getIdCustomer());
    }

    @Test
    public void findByIdCustomer_newCreditLine_creditLine() {
        var creditLine = new CreditLine();
        creditLine.setIdCustomer(100L);
        creditLine.setAmount(30_000.0);

        creditLineRepository.save(creditLine);

        var result = creditLineRepository.findByIdCustomer(100L);

        assertTrue(result.isPresent());
        assertNotNull(result.get().getId());
        assertEquals(30_000.0, result.get().getAmount());
    }

    @Test
    public void findByIdCustomer_nonExistentCreditLine_emptyOptional() {
        var result = creditLineRepository.findByIdCustomer(100L);

        assertFalse(result.isPresent());
    }
}
