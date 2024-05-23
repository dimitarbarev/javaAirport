package sem4.javaAirport.configuration;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import sem4.javaAirport.persistence.*;
import sem4.javaAirport.persistence.entity.*;

@Component
@AllArgsConstructor
public class DatabaseDummyDataInitializer {
    @Autowired
    private ActionRepository actionRepo;

    @Autowired
    private BaggageRepository baggageRepo;

    @Autowired
    private BoardingPassRepository boardingRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private FlightRepository flightRepo;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void populateDatabaseInitialDummyData(){
        if (isDatabaseEmpty())
        {
            CustomerEntity customer = insertCustomer();
            FlightEntity flight = insertFlights();
            BoardingPassEntity boarding = insertBoardingpass(customer, flight);
            insertBaggage(boarding);
            insertEmployees();
        }
    }

    private boolean isDatabaseEmpty() {
        return actionRepo.count() == 0 ||
                baggageRepo.count() == 0 ||
                boardingRepo.count() == 0 ||
                customerRepo.count() == 0 ||
                employeeRepo.count() == 0 ||
                flightRepo.count() == 0;
    }

    private void insertEmployees(){
        EmployeeEntity employee1 = EmployeeEntity.builder()
                .name("Frank")
                .jobTitle("security servant")
                .build();

        employeeRepo.save(employee1);

        EmployeeEntity employee2 = EmployeeEntity.builder()
                .name("Tomas")
                .jobTitle("security servant")
                .build();

        employeeRepo.save(employee2);
    }

    private CustomerEntity insertCustomer(){
        CustomerEntity customer1 = CustomerEntity.builder()
                .name("Jeffry")
                .country("Germany")
                .build();

        customerRepo.save(customer1);

        CustomerEntity customer2 = CustomerEntity.builder()
                .name("Juan")
                .country("Spain")
                .build();

        customerRepo.save(customer2);

        return customer2;
    }

    private FlightEntity insertFlights(){
        FlightEntity flight1 = FlightEntity.builder()
                .panelNumber("FR456")
                .travelCompany("WIZZAIR")
                .build();

        flightRepo.save(flight1);

        FlightEntity flight2 = FlightEntity.builder()
                .panelNumber("TK892")
                .travelCompany("RAYANAIR")
                .build();

        flightRepo.save(flight2);

        return flight2;
    }

    private BoardingPassEntity insertBoardingpass(CustomerEntity customer, FlightEntity flight){
        BoardingPassEntity boardingpass = BoardingPassEntity.builder()
                .customer(customer)
                .flight(flight)
                .build();

        boardingRepo.save(boardingpass);
        return boardingpass;
    }

    private BaggageEntity insertBaggage(BoardingPassEntity boarding){
        BaggageEntity baggage = BaggageEntity.builder()
                .weight(90.0)
                .boardingPass(boarding)
                .status(BaggageStatus.CHECKED_IN)
                .build();

        baggageRepo.save(baggage);
        return baggage;
    }
}
