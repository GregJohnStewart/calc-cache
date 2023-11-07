package org.acme;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.acme.service.pojo.CalculationResult;

import java.math.BigDecimal;
import java.time.ZonedDateTime;


@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@Entity
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
}
