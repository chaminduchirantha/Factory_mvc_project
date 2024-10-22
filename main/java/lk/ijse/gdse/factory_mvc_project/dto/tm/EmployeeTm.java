package lk.ijse.gdse.factory_mvc_project.dto.tm;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class EmployeeTm {
    private String employeeId;
    private String employeeName;
    private String employeeAge;
    private String employeeAddress;
    private String employeeSection;
    private String employeeTask;
    private String employeeContactNumber;
}