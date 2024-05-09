package com.aplazo.scheme.entities;

import com.aplazo.scheme.utils.Util;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Scheme implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idCustomer;
    private Double subTotal;
    private Double installmentAmount;
    private Double rate;
    @Column(name = "is_next_period")
    private Boolean isNextPeriod;

    @Transient
    public Double getCommissionAmount() {
        return Util.round( subTotal * ( rate / 100 ));
    }

    @Transient
    public Double getTotal() {
        return Util.round(subTotal + getCommissionAmount());
    }
}
