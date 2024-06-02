package sem4.javaAirport.business.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sem4.javaAirport.business.IBaggageService;
import sem4.javaAirport.persistence.BaggageRepository;
import sem4.javaAirport.persistence.entity.BaggageEntity;
import sem4.javaAirport.persistence.entity.BaggageStatus;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BaggageService implements IBaggageService {

//    @Autowired
    private final BaggageRepository baggageRepository;

/*    @Transactional
    public void testUpdateBaggageStatus() {
        try {
            updateBaggageStatus(1L, BaggageStatus.SECURITY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    @Override
    @Transactional
    public void updateBaggageStatus(Long baggageId, BaggageStatus newStatus) throws Exception {
        Optional<BaggageEntity> baggageOpt = baggageRepository.findById(baggageId);
        if (baggageOpt.isPresent()) {
            BaggageEntity baggage = baggageOpt.get();
            baggage.setStatus(newStatus);
            baggageRepository.save(baggage);
            System.out.println("Baggage status updated in the database: " + baggage);
        } else {
            System.out.println("Baggage not found with ID: " + baggageId);
        }
    }


    public void moveToNextStatus(Long baggageId) throws Exception {
        if (baggageId == null) {
            throw new IllegalArgumentException("The given id must not be null.");
        }
        try {
            System.out.println("Transaction started for moveToNextStatus");
            BaggageEntity baggage = baggageRepository.findById(baggageId)
                    .orElseThrow(() -> new Exception("Baggage not found with id: " + baggageId));

            BaggageStatus currentStatus = baggage.getStatus();
            BaggageStatus nextStatus = determineNextStatus(currentStatus);
            baggage.setStatus(nextStatus);
            baggageRepository.save(baggage);
            baggageRepository.flush(); // Force the update
            System.out.println("Status updated to: " + nextStatus);
            System.out.println("Transaction completed successfully");
        } catch (Exception e) {
            System.out.println("Error during transaction: " + e.getMessage());
            throw e;
        }
    }


    private BaggageStatus determineNextStatus(BaggageStatus currentStatus) throws Exception {
        switch (currentStatus) {
            case CHECKED_IN:
                return BaggageStatus.SECURITY;
            case SECURITY:
                return BaggageStatus.LOADED;
            case LOADED:
                return BaggageStatus.ARRIVED;
            case ARRIVED:
                return BaggageStatus.ON_BELT;
            case ON_BELT:
                return BaggageStatus.CLAIMED;
            case CLAIMED:
                throw new Exception("Baggage is already claimed.");
            default:
                throw new Exception("Invalid status transition.");
        }
    }

    private boolean needsManualCheck(BaggageEntity baggage) {
        // Example criteria for a manual check
        return isWeightSuspicious(baggage.getWeight()) || isSizeSuspicious(baggage.getSize()) || isRandomlySelected();
    }

    @Transactional
    public void triggerManualCheck(Long baggageId) throws Exception {
        BaggageEntity baggage = baggageRepository.findById(baggageId)
                .orElseThrow(() -> new Exception("Baggage not found with id: " + baggageId));

        baggage.setStatus(BaggageStatus.MANUAL_CHECK);
        baggageRepository.save(baggage);
    }

/*  Suspicious Criteria:
    - Unusual Weight: The baggage weight is unusually high or low.
    - Unusual Dimensions: The baggage size is atypical .
    - Prohibited Items: Specific items detected by scanners(if integrated).
    - Random Selection: Select baggage randomly for a manual check .
    - Behavioral Indicators: Information from the customer or check-in process that indicates the need
    for a closer inspection .
*/


    private boolean isWeightSuspicious(Double weight) {
        // Define weight criteria here
        return weight != null && (weight > 50.0 || weight < 5.0); // Example threshold
    }

    private boolean isSizeSuspicious(String size) {
        // Define your size criteria here
        // Assuming size is a string like "LxWxH"
        if (size != null) {
            String[] dimensions = size.split("x");
            if (dimensions.length == 3) {
                try {
                    double length = Double.parseDouble(dimensions[0]);
                    double width = Double.parseDouble(dimensions[1]);
                    double height = Double.parseDouble(dimensions[2]);
                    // Example size threshold
                    return length > 100 || width > 100 || height > 100;
                } catch (NumberFormatException e) {
                    return true; // If parsing fails, consider it suspicious
                }
            }
        }
        return false;
    }

    private boolean isRandomlySelected() {
        // Implement a random selection criterion (e.g., 5% chance)
        return Math.random() < 0.05;
    }


}
/* @Transactional
    public void moveToNextStatus(Long baggageId) throws Exception {
        BaggageEntity baggage = baggageRepository.findById(baggageId)
                .orElseThrow(() -> new Exception("Baggage not found with id: " + baggageId));

        BaggageStatus currentStatus = baggage.getStatus();
        BaggageStatus nextStatus;

        switch (currentStatus) {
            case CHECKED_IN:
                nextStatus = BaggageStatus.SECURITY;
                break;
            case SECURITY:
                nextStatus = BaggageStatus.LOADED;
                break;
            case LOADED:
                nextStatus = BaggageStatus.ARRIVED;
                break;
            case ARRIVED:
                nextStatus = BaggageStatus.ON_BELT;
                break;
            case ON_BELT:
                nextStatus = BaggageStatus.CLAIMED;
                break;
            case CLAIMED:
                throw new Exception("Baggage is already claimed.");
            default:
                throw new Exception("Invalid status transition.");
        }

        baggage.setStatus(nextStatus);
        baggageRepository.save(baggage);
    }*/