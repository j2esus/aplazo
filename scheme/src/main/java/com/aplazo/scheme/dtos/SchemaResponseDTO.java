package com.aplazo.scheme.dtos;

import com.aplazo.scheme.entities.Scheme;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class SchemaResponseDTO {
    private Long id;
    private Long idCustomer;
    private Double subTotal;
    private Double installmentAmount;
    private Double rate;
    private Boolean isNextPeriod;
    private Double commissionAmount;
    private Double total;
    private Set<LocalDate> paymentDates = new HashSet<>();

    public SchemaResponseDTO(Scheme scheme) {
        this.id = scheme.getId();
        this.idCustomer = scheme.getIdCustomer();
        this.subTotal = scheme.getSubTotal();
        this.installmentAmount = scheme.getInstallmentAmount();
        this.rate = scheme.getRate();
        this.isNextPeriod = scheme.getIsNextPeriod();
        this.commissionAmount = scheme.getCommissionAmount();
        this.total = scheme.getTotal();
        this.paymentDates = scheme.getPaymentDates()
                .stream().map(i -> i.getPaymentDate())
                .collect(Collectors.toSet());
    }
}
