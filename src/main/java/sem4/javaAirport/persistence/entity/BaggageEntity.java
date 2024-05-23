package sem4.javaAirport.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

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

    @Column(name = "code", unique = true)
    private String code;

    @Column(name = "weight")
    private Double weight;

    @ManyToOne
    @JoinColumn(name = "boardingPass_id")
    private BoardingPassEntity boardingPass;

    @Column(name = "status")
    private BaggageStatus status;

    @Column(name = "remark")
    private String remark;

    @OneToMany(mappedBy = "baggage")
    private Set<ActionEntity> actionsSet;

    @PrePersist
    private void generateCode() {
        this.code = UUID.randomUUID().toString();
    }
}
