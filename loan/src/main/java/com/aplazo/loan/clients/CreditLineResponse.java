package com.aplazo.loan.clients;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditLineResponse implements Serializable {
    private Long id;
    private Long idCustomer;
    private Double amount;
}
