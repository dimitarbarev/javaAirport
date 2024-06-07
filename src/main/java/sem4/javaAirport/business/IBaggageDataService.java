package sem4.javaAirport.business;

import sem4.javaAirport.persistence.BaggageDataDTO;

public interface IBaggageDataService {
    BaggageDataDTO getBaggageData(String code);
}
