package sem4.javaAirport.business;

import sem4.javaAirport.persistence.entity.BaggageEntity;
import sem4.javaAirport.persistence.entity.BaggageStatus;

public interface IBaggageService {
    void updateBaggageStatus(Long baggageId, BaggageStatus newStatus) throws Exception;

    void moveToNextStatus(Long baggageId) throws Exception;

    void triggerManualCheck(Long baggageId) throws Exception;

    String getBaggageInfo(Long baggageId) throws Exception;

    boolean isBaggageSuspicious(Long baggageId) throws Exception;

    void approveManualCheck(Long baggageId) throws Exception;


}
