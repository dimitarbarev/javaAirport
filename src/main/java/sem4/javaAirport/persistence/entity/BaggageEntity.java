package sem4.javaAirport.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "baggage")
public class BaggageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "baggage_id")
    private Long baggageId;

    @Column(name = "code")
    private String code;

    @Column(name = "weight")
    private Double weight;

    @ManyToOne
    @JoinColumn(name = "boardingPass_id")
    private BoardingPassEntity boardingPass;

    @Column(name = "status")
    private String status;

    @Column(name = "remark")
    private String remark;
}
