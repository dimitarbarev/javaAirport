package sem4.javaAirport.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "boardingPass")
public class BoardingPassEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "baggage_id")
    private Set<BaggageEntity> baggageSet;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "flight_id")
    private FlightEntity flight;


}
