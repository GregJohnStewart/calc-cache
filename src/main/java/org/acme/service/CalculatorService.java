package org.acme.service;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.acme.CalcAction;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/calculate")
@RegisterRestClient
public interface CalculatorService {
    @Path("{numOne}/{action}/{numTwo}")
    @GET
    Double calculate(
            @PathParam("numOne") Double numOne,
            @PathParam("action") CalcAction action,
            @PathParam("numTwo") Double numTwo
    );
}
