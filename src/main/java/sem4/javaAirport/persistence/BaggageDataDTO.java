package sem4.javaAirport.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sem4.javaAirport.persistence.entity.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaggageDataDTO {
    //baggage specs
    private String code;
    private Double weight;
    private BaggageStatus status;
    private String remark;

    //customer
    private String customersName;

    //flight
    private String travelCompany;
    private String panelNumber;

    //actions
    private Set<ActionDataDTO> actions;

//    public BaggageDataDTO(BaggageEntity baggage, CustomerEntity customer, FlightEntity flight, ) {
//        this.code = baggage.getCode();
//        this.weight = baggage.getWeight();
//        this.status = baggage.getStatus();
//        this.remark = baggage.getRemark();
//        this.boardingPass = new BoardingPassDTO(baggage.getBoardingPass());
//        this.actions = baggage.getActions().stream().map(ActionDTO::new).collect(Collectors.toList());
//    }
}
