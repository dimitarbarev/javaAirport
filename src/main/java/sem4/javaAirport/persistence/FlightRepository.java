package sem4.javaAirport.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sem4.javaAirport.persistence.entity.FlightEntity;

@Repository
public interface FlightRepository extends JpaRepository<FlightEntity,Long> {
}
