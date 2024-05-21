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
@Table(name = "action")
public class ActionEntity {

    @EmbeddedId
    private ActionID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("baggageId")
    @JoinColumn(name = "baggage_id")
    private BaggageEntity baggage;

    @Column(name = "type")
    private String type;

}