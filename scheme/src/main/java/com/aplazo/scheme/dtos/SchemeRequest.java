package com.aplazo.scheme.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchemeRequest {
    private Long idCustomer;
    private Double mount;
}
