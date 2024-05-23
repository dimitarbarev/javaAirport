package sem4.javaAirport.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "name")
    private String name;

    @Column(name = "jobTitle")
    private String jobTitle;

    @OneToMany (mappedBy = "employee")
    private Set<ActionEntity> actionsSet;
}
