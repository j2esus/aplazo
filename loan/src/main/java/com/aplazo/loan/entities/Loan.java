package com.aplazo.loan.entities;

import com.aplazo.loan.clients.PaymentDateResponse;
import com.aplazo.loan.clients.SchemeClient;
import com.aplazo.loan.clients.SchemeResponse;
import com.aplazo.loan.utils.PaymentStatus;
import com.aplazo.loan.utils.Util;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Loan implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate loanDate;
    private Long idCustomer;
    private Double subTotal;
    private Double installmentAmount;
    private Double rate;
    @Column(name = "is_next_period")
    private Boolean isNextPeriod;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_loan", referencedColumnName = "id", nullable = false)
    @OrderBy("paymentDate")
    private Set<Payment> payments = new HashSet<>();

    public Loan(SchemeResponse schemeResponse, Double amount) {
        this.loanDate = LocalDate.now();
        this.idCustomer = schemeResponse.getIdCustomer();
        this.subTotal = amount;
        this.installmentAmount = schemeResponse.getInstallmentAmount();
        this.rate = schemeResponse.getRate();
        this.isNextPeriod = schemeResponse.getIsNextPeriod();
        this.setPaymentDates(schemeResponse.getPaymentDates(), getTotal());
    }

    @Transient
    public Double getCommissionAmount() {
        return Util.round( subTotal * ( rate / 100 ));
    }

    @Transient
    public Double getTotal() {
        return Util.round(subTotal + getCommissionAmount());
    }

    @Transient
    public void setPaymentDates(Set<PaymentDateResponse> paymentDateResponses, Double total) {
        this.payments = paymentDateResponses
                .stream()
                .map(i -> new Payment(i.getPaymentDate(),
                        Util.round(total / paymentDateResponses.size()), PaymentStatus.GENERATED))
                .collect(Collectors.toSet());
    }
}
