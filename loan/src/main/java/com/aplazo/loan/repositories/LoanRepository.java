package com.aplazo.loan.repositories;

import com.aplazo.loan.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByIdCustomer(Long idCustomer);
}
