package org.acme.service.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CalculationRequest {
    @NonNull
    private BigDecimal numOne;
    @NonNull
    private CalcOp op;
    @NonNull
    private BigDecimal numTwo;
}
