package org.acme.interfaces;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.acme.CalcCacheEntry;
import org.acme.service.CalcCacheService;
import org.acme.service.pojo.CalcOp;

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
                numOne, action, numTwo
        );
    }
}
