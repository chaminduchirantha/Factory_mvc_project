package lk.ijse.gdse.factory_mvc_project.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String userId;
    private String userPassword;
    private String userName;
    private String userEmail;
    private String userContactNumber;
    private String branchId;
    private String employeeId;

}
