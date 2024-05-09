package com.aplazo.scheme.dtos;

import com.aplazo.scheme.entities.Scheme;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class SchemaResponse {
    private Long id;
    private Long idCustomer;
    private Double subTotal;
    private Double installmentAmount;
    private Double rate;
    private Boolean isNextPeriod;
    private Double commissionAmount;
    private Double total;
    private Set<LocalDate> payments = new HashSet<>();

    public SchemaResponse(Scheme scheme) {
        this.id = scheme.getId();
        this.idCustomer = scheme.getIdCustomer();
        this.subTotal = scheme.getSubTotal();
        this.installmentAmount = scheme.getInstallmentAmount();
        this.rate = scheme.getRate();
        this.isNextPeriod = scheme.getIsNextPeriod();
        this.commissionAmount = scheme.getCommissionAmount();
        this.total = scheme.getTotal();

    }
}
