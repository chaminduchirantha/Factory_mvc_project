package lk.ijse.gdse.factory_mvc_project.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MachineDto {
    private String machineId;
    private String machineName;
    private String machineTask;
    private String employeeId;
}
