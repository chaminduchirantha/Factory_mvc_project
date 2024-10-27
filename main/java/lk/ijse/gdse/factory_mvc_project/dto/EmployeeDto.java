package lk.ijse.gdse.factory_mvc_project.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class EmployeeDto {
    private String employeeId;
    private String employeeName;
    private int employeeAge;
    private String employeeAddress;
    private String employeeSection;
    private String employeeTask;
    private String employeeContactNumber;
}