package sem4.javaAirport.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sem4.javaAirport.business.IBaggageDataService;
import sem4.javaAirport.persistence.ActionDataDTO;
import sem4.javaAirport.persistence.BaggageDataDTO;
import sem4.javaAirport.persistence.BaggageRepository;
import sem4.javaAirport.persistence.entity.ActionEntity;
import sem4.javaAirport.persistence.entity.BaggageEntity;

import java.util.HashSet;
import java.util.Set;

@Service
public class BaggageDataService implements IBaggageDataService {

    @Autowired
    private BaggageRepository baggageRepo;

    public BaggageDataDTO getBaggageData(String code) {
        BaggageEntity baggage = baggageRepo.findByCode(code);

        return BaggageDataDTO.builder()
                .code(baggage.getCode())
                .weight(baggage.getWeight())
                .status(baggage.getStatus())
                .remark(baggage.getRemark())
                .customersName(baggage.getBoardingPass().getCustomer().getName())
                .travelCompany(baggage.getBoardingPass().getFlight().getTravelCompany())
                .panelNumber(baggage.getBoardingPass().getFlight().getPanelNumber())
                .actions(convertToActionDTOs(baggage.getActionsSet()))
                .build();

    }

    public Set<ActionDataDTO> convertToActionDTOs(Set<ActionEntity> actionEntitySet){
        if (actionEntitySet.isEmpty()){
            return null;
        }
        else {
            Set<ActionDataDTO> actionDataDTOSet = new HashSet<>();
            for (ActionEntity one : actionEntitySet){
                actionDataDTOSet.add(ActionDataDTO.builder()
                        .datetime(one.getDatetime())
                        .type(one.getType())
                        .employeeName(one.getEmployee().getName())
                        .employeeJobTitle(one.getEmployee().getJobTitle())
                        .build());
            }
            return actionDataDTOSet;
        }
    }
}
