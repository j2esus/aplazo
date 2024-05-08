package com.aplazo.creditline.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditLine implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idCustomer;
    private Double amount;
}
