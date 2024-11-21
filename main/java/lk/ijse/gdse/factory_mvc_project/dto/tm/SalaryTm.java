package lk.ijse.gdse.factory_mvc_project.dto.tm;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class SalaryTm {
    private String salaryId;
    private String salaryFees;
    private LocalDate salaryReleaseDate;
    private String basicSalary;
    private String paymentMethod;
    private String employeeId;
}

