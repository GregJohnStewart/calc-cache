package org.acme.interfaces;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.acme.CalcAction;
import org.acme.CalcResponse;
import org.acme.service.CalcCacheService;

@Slf4j
@Path("/calculate")
public class CacheResource {

    @Inject
    CalcCacheService calcCacheService;

    @Path("{numOne}/{action}/{numTwo}")
    @GET
    public CalcResponse calculate(
            @PathParam("numOne") String numOne,
            @PathParam("action") CalcAction action,
            @PathParam("numTwo") String numTwo
    ) {
        log.info("Calculating.");
        return this.calcCacheService.calculate(
                Double.parseDouble(numOne), action, Double.parseDouble(numTwo)
        );
    }
}
