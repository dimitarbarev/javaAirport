package sem4.javaAirport.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "boardingPass")
public class BoardingPassEntity {

    @EmbeddedId
    private BoardingPassID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("customerId")
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("baggageId")
    @JoinColumn(name = "baggage_id")
    private BaggageEntity baggage;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("flightId")
    @JoinColumn(name = "flight_id")
    private FlightEntity flight;


}
