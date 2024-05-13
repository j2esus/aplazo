package com.aplazo.loan.repositories;

import com.aplazo.loan.entities.Loan;
import com.aplazo.loan.entities.Payment;
import com.aplazo.loan.utils.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByIdCustomer(Long idCustomer);

    @Query("select p from Loan l join l.payments p where p.paymentDate <= ?1 and p.status in ( ?2 )")
    List<Payment> findPayments(LocalDate date, List<PaymentStatus> status);

    @Modifying
    @Query("UPDATE Payment p SET p.status = ?1 WHERE p.id = ?2 ")
    int updatePaymentStatus(PaymentStatus status, Long id);

    @Query("select p from Loan l join l.payments p where l.idCustomer <= ?1 and p.status in ( ?2 )")
    List<Payment> findPayments(Long idCustomer, List<PaymentStatus> status);
}
