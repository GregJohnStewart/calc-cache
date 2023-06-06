package org.acme.interfaces;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.acme.CalcAction;
import org.acme.CalcResponse;
import org.acme.service.CalcCacheService;

@Path("/calculator")
public class CacheResource {

    @Inject
    CalcCacheService calcCacheService;

    @Path("{numOne}/{action}/{numTwo}")
    @GET
    public CalcResponse calculate(
            @PathParam("numOne") Double numOne,
            @PathParam("action") CalcAction action,
            @PathParam("numTwo") Double numTwo
    ) {
        return this.calcCacheService.calculate(
                numOne, action, numTwo
        );
    }
}
