package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.acme.CalcAction;
import org.acme.CalcCacheEntry;
import org.acme.service.pojo.CalcOp;
import org.acme.service.pojo.CalculationResult;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.math.BigDecimal;


@Slf4j
@ApplicationScoped
public class CalcCacheService {

    @RestClient
    CalculatorService calculatorService;

    @Inject
    EntityManager entityManager;

    @Transactional
    public CalcCacheEntry calculate(
            BigDecimal numOne,
            CalcOp action,
            BigDecimal numTwo
    ) {
        try {
            CalcCacheEntry response = this.entityManager
                    .createQuery(
                            "SELECT r FROM CalcCacheEntry r WHERE r.argOne=?1 AND r.action=?2 AND r.argTwo=?3",
                            CalcCacheEntry.class
                    )
                    .setParameter(1, numOne)
                    .setParameter(2, action)
                    .setParameter(3, numTwo)
                    .getSingleResult();

            if (response != null) {
                return response;
            }
        } catch (NoResultException | NonUniqueResultException e) {
            log.info("Cache miss. Calling service.");
        }

        CalculationResult response = this.calculatorService.calculate(
                numOne,
                action,
                numTwo
        );

        CalcCacheEntry newEntry = CalcCacheEntry.builder()
                .numOne(response.getNumOne())
                .numTwo(response.getNumTwo())
                .op(response.getOp())
                .result(response.getResult())
                .build();

        this.entityManager.persist(newEntry);

        return newEntry;
    }

    @Transactional
    public CalcCacheEntry addToCache(CalcCacheEntry response){
        this.entityManager.persist(response);
        return response;
    }
}
