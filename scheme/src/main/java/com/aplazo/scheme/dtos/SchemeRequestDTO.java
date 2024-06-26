package com.aplazo.scheme.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchemeRequestDTO {
    private Long idCustomer;
    private Double amount;
}
