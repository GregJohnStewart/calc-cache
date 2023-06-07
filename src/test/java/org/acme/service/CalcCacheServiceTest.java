package org.acme.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.acme.CalcAction;
import org.acme.CalcResponse;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
public class CalcCacheServiceTest {

    @Inject
    CalcCacheService calcCacheService;

    @Inject
    EntityManager entityManager;

    @Test
    public void testAddCacheEntry(){
        CalcResponse response = CalcResponse.builder()
                .argOne(1.0)
                .action(CalcAction.ADD)
                .argTwo(2.0)
                .answer(3.0)
                .build();

        this.calcCacheService.addToCache(response);

        log.info("Persisted response 1: {}", response);
    }
    @Test
    public void testAddCacheEntryTwo(){
        CalcResponse response = CalcResponse.builder()
                .argOne(1.0)
                .action(CalcAction.ADD)
                .argTwo(2.0)
                .answer(3.0)
                .build();

        this.calcCacheService.addToCache(response);

        log.info("Persisted response 2: {}", response);
    }
}
