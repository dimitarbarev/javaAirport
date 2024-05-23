package sem4.javaAirport.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sem4.javaAirport.persistence.entity.ActionEntity;
import sem4.javaAirport.persistence.entity.ActionID;

@Repository
public interface ActionRepository extends JpaRepository<ActionEntity, Long> {
}
