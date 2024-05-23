package sem4.javaAirport.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sem4.javaAirport.persistence.entity.BoardingPassEntity;
import sem4.javaAirport.persistence.entity.BoardingPassID;

@Repository
public interface BoardingPassRepository extends JpaRepository<BoardingPassEntity, Long> {
}
