package lk.ijse.gdse.factory_mvc_project.dto.tm;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTm {
    private String paymentId;
    private LocalDate paymentDate;
    private double paymentAmount;
    private double discount;
    private String supplierId;
}