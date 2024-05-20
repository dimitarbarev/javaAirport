package sem4.javaAirport.persistence.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardingPassID implements Serializable {//for the composite keys on the DB
    private Long customerId;
    private Long baggageId;
    private Long flightId;


}
