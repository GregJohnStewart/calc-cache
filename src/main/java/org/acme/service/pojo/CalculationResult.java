package org.acme.service.pojo;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public class CalculationResult extends CalculationRequest {
    @NonNull
    private BigDecimal result;

    public static CalculationResult.CalculationResultBuilder<?, ?> fromRequest(CalculationRequest request){
        return CalculationResult.builder()
                .numOne(request.getNumOne())
                .op(request.getOp())
                .numTwo(request.getNumTwo());
    }
}
