package lk.ijse.gdse.factory_mvc_project.dto.tm;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SalaryTm {
    private String salaryId;
    private String salaryFees;
    private String salaryReleaseDate;
    private String basicSalary;
    private String employeeId;
}

