package sem4.javaAirport.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sem4.javaAirport.persistence.entity.BaggageStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaggageStatusDTO {
    private Long baggageId;
    private BaggageStatus newStatus;

    // Constructors, getters and setters
}

