package lk.ijse.gdse.factory_mvc_project.dto.tm;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BranchTm {
    private String branchId;
    private String branchName;
    private String branchLocation;
    private int numberOfEmployee;
    private String ItemCode;
}