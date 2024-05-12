package com.aplazo.loan.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanRequestDTO {
    private Long idCustomer;
    private Double amount;
    private Long idScheme;
}
