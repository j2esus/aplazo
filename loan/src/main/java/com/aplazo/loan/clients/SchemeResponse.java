package com.aplazo.loan.clients;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchemeResponse implements Serializable {
    private Long id;
    private Long idCustomer;
    private Double subTotal;
    private Double installmentAmount;
    private Double rate;
    private Boolean isNextPeriod;
    private Double commissionAmount;
    private Double total;

    private Set<PaymentDateResponse> paymentDates = new HashSet<>();
}
