package org.acme.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.acme.CalcAction;
import org.acme.CalcCacheEntry;
import org.acme.service.pojo.CalcOp;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

@Slf4j
@QuarkusTest
public class CalcCacheServiceTest {

    @Inject
    CalcCacheService calcCacheService;

    @Inject
    EntityManager entityManager;

    @Test
    public void testAddCacheEntry(){
        CalcCacheEntry response = CalcCacheEntry.builder()
                .numOne(new BigDecimal("1.0"))
                .op(CalcOp.ADD)
                .numTwo(new BigDecimal("2.0"))
                .result(new BigDecimal("3.0"))
                .build();

        this.calcCacheService.addToCache(response);

        log.info("Persisted response 1: {}", response);
    }
    @Test
    public void testAddCacheEntryTwo(){
        CalcCacheEntry response = CalcCacheEntry.builder()
                .numOne(new BigDecimal("1.0"))
                .op(CalcOp.ADD)
                .numTwo(new BigDecimal("2.0"))
                .result(new BigDecimal("3.0"))
                .build();

        this.calcCacheService.addToCache(response);

        log.info("Persisted response 2: {}", response);
    }
}
