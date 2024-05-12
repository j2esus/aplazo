package com.aplazo.loan.entities;

import com.aplazo.loan.utils.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate paymentDate;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    public Payment(LocalDate date, Double amount, PaymentStatus status) {
        this.paymentDate = date;
        this.amount = amount;
        this.status = status;
    }
}
