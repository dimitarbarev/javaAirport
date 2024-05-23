package sem4.javaAirport.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sem4.javaAirport.persistence.entity.CustomerEntity;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
