package sem4.javaAirport.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActionDataDTO {
    //action data
    private Date datetime;
    private String type;

    //employee data
    private String employeeName;
    private String employeeJobTitle;
}
