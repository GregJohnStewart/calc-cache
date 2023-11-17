package org.acme.service;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.acme.CalcAction;
import org.acme.service.pojo.CalcOp;
import org.acme.service.pojo.CalculationRequest;
import org.acme.service.pojo.CalculationResult;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.math.BigDecimal;

@RegisterRestClient(configKey = "calc-service", baseUri = "/calculate")
public interface CalculatorService {

    @PUT
    CalculationResult calculate(
            CalculationRequest request
    );
}
