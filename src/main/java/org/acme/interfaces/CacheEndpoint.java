package org.acme.interfaces;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.acme.service.pojo.CalcCacheEntry;
import org.acme.service.CalcCacheService;
import org.acme.service.pojo.CalcOp;
import org.acme.service.pojo.CalculationRequest;

import java.math.BigDecimal;

@Slf4j
@Path("/calculate")
public class CacheEndpoint {

    @Inject
    CalcCacheService calcCacheService;

    @Path("{numOne}/{action}/{numTwo}")
    @GET
    public CalcCacheEntry calculate(
            @PathParam("numOne") BigDecimal numOne,
            @PathParam("action") CalcOp action,
            @PathParam("numTwo") BigDecimal numTwo
    ) {
        log.info("Calculating.");
        return this.calcCacheService.calculate(
                CalculationRequest.builder().numOne(numOne).numTwo(numTwo).op(action).build()
        );
    }
}
