package sem4.javaAirport.business;

import sem4.javaAirport.persistence.entity.BaggageStatus;

public interface IBaggageService {
    void updateBaggageStatus(Long baggageId, BaggageStatus newStatus) throws Exception;
}
