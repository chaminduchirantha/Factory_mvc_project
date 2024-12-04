package lk.ijse.gdse.factory_mvc_project.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDetailDto {
    private String supplierId;
    private String itemId;
    private String supplierRatings;
}