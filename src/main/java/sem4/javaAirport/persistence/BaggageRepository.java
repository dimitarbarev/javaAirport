package sem4.javaAirport.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sem4.javaAirport.persistence.entity.BaggageEntity;

@Repository
public interface BaggageRepository extends JpaRepository<BaggageEntity, Long> {
    BaggageEntity findByCode(String code);
}
