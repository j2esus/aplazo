package com.aplazo.loan.clients;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDateResponse implements Serializable {
    private Long id;
    private LocalDate paymentDate;
}
