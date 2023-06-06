package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
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

    public CalcResponse calculate(
            Double numOne,
            CalcAction action,
            Double numTwo
    ) {
        try {
            CalcResponse response = this.entityManager.createQuery("SELECT * FROM CalcResponse WHERE CalcResponse.argOne=?1 AND CalcResponse.action=?2 AND CalcResponse.argTwo=?3 LIMIT 1;", CalcResponse.class)
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

        CalcResponse.CalcResponseBuilder responseBuilder = CalcResponse.builder()
                .argOne(numOne)
                .action(action)
                .argTwo(numTwo)
                .answer(this.calculatorService.calculate(
                        numOne,
                        action,
                        numTwo
                ));

        return responseBuilder.build();
    }
}
