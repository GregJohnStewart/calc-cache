package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.acme.CalcAction;
import org.acme.CalcResponse;
import org.eclipse.microprofile.rest.client.inject.RestClient;


@Slf4j
@ApplicationScoped
public class CalcCacheService {

    @RestClient
    CalculatorService calculatorService;

    @Inject
    EntityManager entityManager;

    @Transactional
    public CalcResponse calculate(
            Double numOne,
            CalcAction action,
            Double numTwo
    ) {
        try {
            CalcResponse response = this.entityManager
                    .createQuery(
                            "SELECT r FROM CalcResponse r WHERE r.argOne=?1 AND r.action=?2 AND r.argTwo=?3",
                            CalcResponse.class
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

        CalcResponse response = CalcResponse.builder()
                .argOne(numOne)
                .action(action)
                .argTwo(numTwo)
                .answer(this.calculatorService.calculate(
                        Double.toString(numOne),
                        action,
                        Double.toString(numTwo)
                ))
                .build();

        this.entityManager.persist(response);

        return response;
    }

    @Transactional
    public CalcResponse addToCache(CalcResponse response){
        this.entityManager.persist(response);
        return response;
    }
}
