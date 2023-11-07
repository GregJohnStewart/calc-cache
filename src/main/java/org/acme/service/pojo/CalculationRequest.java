package org.acme.service.pojo;

import jakarta.persistence.MappedSuperclass;
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
@MappedSuperclass
public class CalculationRequest {
    @NonNull
    private BigDecimal numOne;
    @NonNull
    private CalcOp op;
    @NonNull
    private BigDecimal numTwo;
}
