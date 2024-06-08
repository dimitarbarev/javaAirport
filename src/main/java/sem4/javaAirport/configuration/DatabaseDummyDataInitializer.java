package sem4.javaAirport.configuration;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import sem4.javaAirport.persistence.*;
import sem4.javaAirport.persistence.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@AllArgsConstructor
public class DatabaseDummyDataInitializer {

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
    public void populateDatabaseInitialDummyData() {
        if (isDatabaseEmpty()) {
            List<CustomerEntity> customers = insertCustomers();
            List<FlightEntity> flights = insertFlights();
            List<BoardingPassEntity> boardings = insertBoardingPasses(customers, flights);
            insertBaggage(boardings);
            insertEmployees();
        }
    }

    private boolean isDatabaseEmpty() {
        return baggageRepo.count() == 0 ||
                boardingRepo.count() == 0 ||
                customerRepo.count() == 0 ||
                employeeRepo.count() == 0 ||
                flightRepo.count() == 0;
    }

    private void insertEmployees() {
        List<EmployeeEntity> employees = List.of(
                EmployeeEntity.builder().name("Frank").jobTitle("security servant").build(),
                EmployeeEntity.builder().name("Tomas").jobTitle("security servant").build(),
                EmployeeEntity.builder().name("Greg").jobTitle("desk officer").build(),
                EmployeeEntity.builder().name("Tobias").jobTitle("desk officer").build(),
                EmployeeEntity.builder().name("Kim").jobTitle("special forces").build()
        );
        employeeRepo.saveAll(employees);
    }

    private List<CustomerEntity> insertCustomers() {
        List<CustomerEntity> customers = List.of(
                CustomerEntity.builder().name("Jeffry").country("Germany").build(),
                CustomerEntity.builder().name("Juan").country("Spain").build(),
                CustomerEntity.builder().name("Klaudio").country("Venezuela").build(),
                CustomerEntity.builder().name("Vezuvii").country("Italy").build(),
                CustomerEntity.builder().name("Shiridar").country("Kazakhstan").build()
        );
        customerRepo.saveAll(customers);
        return new ArrayList<>(customers);
    }

    private List<FlightEntity> insertFlights() {
        List<FlightEntity> flights = List.of(
                FlightEntity.builder().panelNumber("FR456").travelCompany("WIZZAIR").build(),
                FlightEntity.builder().panelNumber("TK892").travelCompany("RYANAIR").build(),
                FlightEntity.builder().panelNumber("TU823").travelCompany("RYANAIR").build(),
                FlightEntity.builder().panelNumber("PY167").travelCompany("RYANAIR").build(),
                FlightEntity.builder().panelNumber("KL954").travelCompany("WIZZAIR").build()
        );
        flightRepo.saveAll(flights);
        return new ArrayList<>(flights);
    }

    private List<BoardingPassEntity> insertBoardingPasses(List<CustomerEntity> customers, List<FlightEntity> flights) {
        Random randomGen = new Random();
        List<BoardingPassEntity> boardings = new ArrayList<>();

        for (CustomerEntity customer : customers) {
            int flightIndex = randomGen.nextInt(flights.size());
            BoardingPassEntity boarding = BoardingPassEntity.builder()
                    .customer(customer)
                    .flight(flights.get(flightIndex))
                    .build();
            boardings.add(boarding);
            boardingRepo.save(boarding);
        }
        return boardings;
    }

    private void insertBaggage(List<BoardingPassEntity> boardings) {
        List<BaggageEntity> baggageList = new ArrayList<>();
        for (BoardingPassEntity boarding : boardings) {
            BaggageEntity baggage = BaggageEntity.builder()
                    .weight(90.0)
                    .boardingPass(boarding)
                    .status(BaggageStatus.CHECKED_IN)
                    .build();
            baggageList.add(baggage);
        }
        baggageRepo.saveAll(baggageList);
    }
}
