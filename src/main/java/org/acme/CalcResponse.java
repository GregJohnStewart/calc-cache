package org.acme;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;


@Data
@Entity
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class CalcResponse {
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private Double answer;
    @NonNull
    private Double argOne;
    @NonNull
    private CalcAction action;
    @NonNull
    private Double argTwo;

    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    private ZonedDateTime date = ZonedDateTime.now();
}
