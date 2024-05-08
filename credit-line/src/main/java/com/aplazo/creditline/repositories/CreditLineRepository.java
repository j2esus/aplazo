package com.aplazo.creditline.repositories;

import com.aplazo.creditline.entities.CreditLine;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CreditLineRepository extends JpaRepository<CreditLine, Long> {
    Optional<CreditLine> findByIdCustomer(Long idCustomer);
}
