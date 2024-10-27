package lk.ijse.gdse.factory_mvc_project.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String productId;
    private String productName;
    private double productPrice;
    private LocalDate productDate;
    private String productRatings;
}
