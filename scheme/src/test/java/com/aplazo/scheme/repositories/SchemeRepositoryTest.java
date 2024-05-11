package com.aplazo.scheme.repositories;

import com.aplazo.scheme.BaseContainer;
import com.aplazo.scheme.entities.Scheme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SchemeRepositoryTest extends BaseContainer {

    @Autowired
    SchemeRepository schemeRepository;

    @BeforeEach
    public void before() {
        schemeRepository.deleteAll();
    }

    @Test
    public void save_newSchemeWithoutDecimals_schemeWithId() {
        var scheme = new Scheme();
        scheme.setRate(10.0);
        scheme.setIdCustomer(100L);
        scheme.setSubTotal(5_000.0);
        scheme.setIsNextPeriod(false);

        var result = schemeRepository.save(scheme);

        assertNotNull(result.getId());
        assertEquals(10.0, result.getRate());
        assertEquals(100L, result.getIdCustomer());
        assertEquals(5_000D, result.getSubTotal());
        assertEquals(500.0, result.getCommissionAmount());
        assertEquals(5_500D, result.getTotal());
    }

    @Test
    public void save_newSchemeWithTwoDecimals_schemeWithId() {
        var scheme = new Scheme();
        scheme.setRate(10.0);
        scheme.setIdCustomer(100L);
        scheme.setSubTotal(5_356.78);

        var result = schemeRepository.save(scheme);

        assertNotNull(result.getId());
        assertEquals(10.0, result.getRate());
        assertEquals(100L, result.getIdCustomer());
        assertEquals(5_356.78, result.getSubTotal());
        assertEquals(535.68, result.getCommissionAmount());
        assertEquals(5892.46, result.getTotal());
    }

    @Test
    public void save_newSchemeWithMoreThanTwoDecimals_schemeWithId() {
        var scheme = new Scheme();
        scheme.setRate(10.0);
        scheme.setIdCustomer(100L);
        scheme.setSubTotal(4_356.154251841);

        var result = schemeRepository.save(scheme);

        assertNotNull(result.getId());
        assertEquals(10.0, result.getRate());
        assertEquals(100L, result.getIdCustomer());
        assertEquals(4_356.154251841, result.getSubTotal());
        assertEquals(435.62, result.getCommissionAmount());
        assertEquals(4791.77, result.getTotal());
    }

    @Test
    public void findByIdCustomer_nonExistentSchemes_emptyList() {
        var result = schemeRepository.findByIdCustomer(100L);
        assertTrue(result.isEmpty());
    }

    @Test
    public void findByIdCustomer_withOneExistentScheme_listWithOneElement() {
        var scheme = new Scheme();
        scheme.setRate(10.0);
        scheme.setIdCustomer(100L);
        scheme.setSubTotal(4_356.154251841);
        scheme.setIsNextPeriod(false);

        schemeRepository.save(scheme);

        var result = schemeRepository.findByIdCustomer(100L);
        assertEquals(1, result.size());
    }

    @Test
    public void findByIdCustomer_withTwoExistentScheme_listWithTwoElements() {
        var scheme1 = new Scheme();
        scheme1.setRate(10.0);
        scheme1.setIdCustomer(100L);
        scheme1.setSubTotal(4_356.154251841);
        scheme1.setIsNextPeriod(true);

        schemeRepository.save(scheme1);

        var scheme2 = new Scheme();
        scheme2.setRate(10.0);
        scheme2.setIdCustomer(100L);
        scheme2.setSubTotal(4_356.154251841);
        scheme2.setIsNextPeriod(false);

        schemeRepository.save(scheme2);

        var result = schemeRepository.findByIdCustomer(100L);
        assertEquals(2, result.size());
    }
}
