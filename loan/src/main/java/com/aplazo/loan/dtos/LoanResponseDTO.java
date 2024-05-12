package com.aplazo.loan.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanResponseDTO {
    private Long id;
    private String status;
}
