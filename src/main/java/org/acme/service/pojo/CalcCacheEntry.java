package org.acme.service.pojo;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;


@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@Entity
@Inheritance
@SuperBuilder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class CalcCacheEntry extends CalculationResult {
    @Id
    @GeneratedValue
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    private ZonedDateTime date = ZonedDateTime.now();

    public static CalculationResult.CalculationResultBuilder<?, ?> fromResult(CalculationResult result){
        return CalcCacheEntry.builder()
                .numOne(result.getNumOne())
                .op(result.getOp())
                .numTwo(result.getNumTwo())
                .result(result.getResult());
    }
}
