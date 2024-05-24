package sem4.javaAirport.business.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sem4.javaAirport.business.IBaggageService;
import sem4.javaAirport.domain.BaggageStatusDTO;
import sem4.javaAirport.persistence.BaggageRepository;
import sem4.javaAirport.persistence.entity.BaggageEntity;
import sem4.javaAirport.persistence.entity.BaggageStatus;

@Service
@AllArgsConstructor
public class BaggageService implements IBaggageService {

    @Autowired
    private BaggageRepository baggageRepository;


    @Override
    public void updateBaggageStatus(Long baggageId, BaggageStatus newStatus) throws Exception {
        BaggageEntity baggage = baggageRepository.findById(baggageId)
                .orElseThrow(() -> new Exception("Baggage not found with id: " + baggageId));
        baggage.setStatus(newStatus);
        baggageRepository.save(baggage);
    }
}
