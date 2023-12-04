package org.acme.service;

import io.quarkus.scheduler.Scheduled;
import io.smallrye.common.annotation.Blocking;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.acme.service.pojo.CalcCacheEntry;
import org.acme.service.pojo.CalcOp;
import org.acme.service.pojo.CalculationRequest;
import org.acme.service.pojo.CalculationResult;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;


@Slf4j
@ApplicationScoped
public class CalcCacheService {

    private static final Random RAND = new SecureRandom();
    private static BigDecimal max = new BigDecimal("9001.0");

    @RestClient
    CalculatorService calculatorService;

    @Channel("calculator-requests")
    Emitter<CalculationRequest> quoteRequestEmitter;

    @Inject
    EntityManager entityManager;

    public Optional<CalcCacheEntry> getFromCache(
            CalculationRequest request
    ){
        try {
            CalcCacheEntry response = this.entityManager
                    .createQuery(
                            "SELECT r FROM CalcCacheEntry r WHERE r.numOne=?1 AND r.op=?2 AND r.numTwo=?3",
                            CalcCacheEntry.class
                    )
                    .setParameter(1, request.getNumOne())
                    .setParameter(2, request.getOp())
                    .setParameter(3, request.getNumTwo())
                    .getSingleResult();

            if (response != null) {
                return Optional.of(response);
            }
        } catch (NoResultException | NonUniqueResultException e) {
            log.info("Cache miss. Calling service.");
        }
        return Optional.empty();
    }

    @Transactional
    public CalcCacheEntry calculate(
            CalculationRequest request
    ) {
        Optional<CalcCacheEntry> cacheOp = this.getFromCache(request);

        if(cacheOp.isPresent()){
            return cacheOp.get();
        }

        CalcCacheEntry newEntry = (CalcCacheEntry) CalcCacheEntry.fromResult(
                this.calculatorService.calculate(request)
        ).build();

        this.entityManager.persist(newEntry);

        return newEntry;
    }

    @Transactional
    public CalcCacheEntry addToCache(CalculationResult response) {
        Optional<CalcCacheEntry> entryOp =  this.getFromCache(response);

        if(entryOp.isPresent()){
            if(response.getResult().equals(entryOp.get().getResult())){
                log.info("Already in cache: {}", entryOp.get());
                return entryOp.get();
            }
            log.warn("Had the request in cache, but answer was different. Updating to new result.");
        }

        CalcCacheEntry newEntry = (CalcCacheEntry) CalcCacheEntry.fromResult(response).build();

        this.entityManager.persist(newEntry);
        return newEntry;
    }

    @Incoming("calculator-results")
    @Blocking
    public void process(JsonObject resultJson) {
        log.info("Got result from Queue: {}", resultJson);
        CalculationResult result = resultJson.mapTo(CalculationResult.class);
        this.addToCache(result);
    }

    @Scheduled(every="10s")
    void populateCache() {
        log.info("Populating cache");

        this.quoteRequestEmitter.send(
                CalculationRequest.builder()
                        .numOne(
                                BigDecimal.valueOf(RAND.nextDouble())
                                        .divide(max, RoundingMode.HALF_UP)
                        )
                        .numTwo(
                                BigDecimal.valueOf(RAND.nextDouble())
                                        .divide(max, RoundingMode.HALF_UP)
                        )
                        .op(CalcOp.values()[RAND.nextInt(CalcOp.values().length)])
                        .build()
        );
    }
}
