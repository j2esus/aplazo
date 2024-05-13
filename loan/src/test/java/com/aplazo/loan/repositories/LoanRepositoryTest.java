package com.aplazo.loan.repositories;

import com.aplazo.loan.BaseContainer;
import com.aplazo.loan.entities.Loan;
import com.aplazo.loan.entities.Payment;
import com.aplazo.loan.utils.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LoanRepositoryTest extends BaseContainer {

    @Autowired
    LoanRepository loanRepository;

    @BeforeEach
    public void before() {
        loanRepository.deleteAll();
    }

    @Test
    public void save_newLoanWithoutDecimals_loanWithId() {
        var loan = new Loan();
        loan.setRate(10.0);
        loan.setIdCustomer(100L);
        loan.setSubTotal(5_000.0);
        loan.setIsNextPeriod(false);

        var result = loanRepository.save(loan);

        assertNotNull(result.getId());
        assertEquals(10.0, result.getRate());
        assertEquals(100L, result.getIdCustomer());
        assertEquals(5_000D, result.getSubTotal());
        assertEquals(500.0, result.getCommissionAmount());
        assertEquals(5_500D, result.getTotal());
    }

    @Test
    public void save_newLoanWithTwoDecimals_loanWithId() {
        var loan = new Loan();
        loan.setRate(10.0);
        loan.setIdCustomer(100L);
        loan.setSubTotal(5_356.78);

        var result = loanRepository.save(loan);

        assertNotNull(result.getId());
        assertEquals(10.0, result.getRate());
        assertEquals(100L, result.getIdCustomer());
        assertEquals(5_356.78, result.getSubTotal());
        assertEquals(535.68, result.getCommissionAmount());
        assertEquals(5892.46, result.getTotal());
    }

    @Test
    public void save_newLoanWithMoreThanTwoDecimals_loanWithId() {
        var loan = new Loan();
        loan.setRate(10.0);
        loan.setIdCustomer(100L);
        loan.setSubTotal(4_356.154251841);

        var result = loanRepository.save(loan);

        assertNotNull(result.getId());
        assertEquals(10.0, result.getRate());
        assertEquals(100L, result.getIdCustomer());
        assertEquals(4_356.154251841, result.getSubTotal());
        assertEquals(435.62, result.getCommissionAmount());
        assertEquals(4791.77, result.getTotal());
    }

    @Test
    public void findByIdCustomer_nonExistentLoans_emptyList() {
        var result = loanRepository.findByIdCustomer(100L);
        assertTrue(result.isEmpty());
    }

    @Test
    public void findByIdCustomer_withOneExistentLoan_listWithOneElement() {
        var loan = new Loan();
        loan.setRate(10.0);
        loan.setIdCustomer(100L);
        loan.setSubTotal(4_356.154251841);
        loan.setIsNextPeriod(false);

        loanRepository.save(loan);

        var result = loanRepository.findByIdCustomer(100L);
        assertEquals(1, result.size());
    }

    @Test
    public void findByIdCustomer_withTwoExistentLoans_listWithTwoElements() {
        var loan1 = new Loan();
        loan1.setRate(10.0);
        loan1.setIdCustomer(100L);
        loan1.setSubTotal(4_356.154251841);
        loan1.setIsNextPeriod(true);

        loanRepository.save(loan1);

        var loan2 = new Loan();
        loan2.setRate(10.0);
        loan2.setIdCustomer(100L);
        loan2.setSubTotal(4_356.154251841);
        loan2.setIsNextPeriod(false);

        loanRepository.save(loan2);

        var result = loanRepository.findByIdCustomer(100L);
        assertEquals(2, result.size());
    }

    @Test
    public void save_LoanWithPayments_loanSavedWithOnePayment() {
        var loan = new Loan();
        loan.setRate(10.0);
        loan.setIdCustomer(100L);
        loan.setSubTotal(5_000.0);
        loan.setIsNextPeriod(false);

        loan.getPayments().add(new Payment(LocalDate.now(), loan.getTotal(), PaymentStatus.GENERATED));

        var result = loanRepository.save(loan);

        assertNotNull(result.getId());
        assertEquals(1, result.getPayments().size());
    }

    @Test
    public void save_loanWithPayments_loanSavedWithTwoPayment() {
        var loan = new Loan();
        loan.setRate(10.0);
        loan.setIdCustomer(100L);
        loan.setSubTotal(5_000.0);
        loan.setIsNextPeriod(false);

        loan.getPayments().add(new Payment(LocalDate.now(), loan.getTotal(), PaymentStatus.GENERATED));
        loan.getPayments().add(new Payment(LocalDate.now().plusDays(15), loan.getTotal(), PaymentStatus.GENERATED));

        var result = loanRepository.save(loan);

        assertNotNull(result.getId());
        assertEquals(2, result.getPayments().size());
    }

    @Test
    public void findPayments_onlyOnePayment_listWithOneElement() {
        var loan = new Loan();
        loan.setLoanDate(LocalDate.now());
        loan.setIdCustomer(100L);
        loan.setRate(10D);
        loan.setSubTotal(1_000D);
        loan.setIsNextPeriod(false);
        loan.setInstallmentAmount(0D);

        var payment = new Payment();
        payment.setAmount(1_000D);
        payment.setStatus(PaymentStatus.GENERATED);
        payment.setPaymentDate(LocalDate.now());

        loan.getPayments().add(payment);

        this.loanRepository.save(loan);

        List<Payment> paymentList = loanRepository.findPayments(LocalDate.now(), List.of(PaymentStatus.GENERATED));

        assertEquals(1, paymentList.size());
    }

    @Test
    public void findPayments_twoPaymentsOneWithStatusNotAllowed_listWithOneElement() {
        var loan = new Loan();
        loan.setLoanDate(LocalDate.now());
        loan.setIdCustomer(100L);
        loan.setRate(10D);
        loan.setSubTotal(2_000D);
        loan.setIsNextPeriod(false);
        loan.setInstallmentAmount(0D);

        var payment = new Payment();
        payment.setAmount(1_000D);
        payment.setStatus(PaymentStatus.GENERATED);
        payment.setPaymentDate(LocalDate.now());

        var payment2 = new Payment();
        payment2.setAmount(1_000D);
        payment2.setStatus(PaymentStatus.PROCESSED);
        payment2.setPaymentDate(LocalDate.now());

        loan.getPayments().add(payment);
        loan.getPayments().add(payment2);

        this.loanRepository.save(loan);

        List<Payment> paymentList = loanRepository.findPayments(LocalDate.now(),
                List.of(PaymentStatus.GENERATED));

        assertEquals(1, paymentList.size());
    }

    @Test
    public void findPayments_twoPaymentsOneWithTwoStatusAllowed_listWithTwoElement() {
        var loan = new Loan();
        loan.setLoanDate(LocalDate.now());
        loan.setIdCustomer(100L);
        loan.setRate(10D);
        loan.setSubTotal(2_000D);
        loan.setIsNextPeriod(false);
        loan.setInstallmentAmount(0D);

        var payment = new Payment();
        payment.setAmount(1_000D);
        payment.setStatus(PaymentStatus.GENERATED);
        payment.setPaymentDate(LocalDate.now());

        var payment2 = new Payment();
        payment2.setAmount(1_000D);
        payment2.setStatus(PaymentStatus.FAILED);
        payment2.setPaymentDate(LocalDate.now());

        loan.getPayments().add(payment);
        loan.getPayments().add(payment2);

        this.loanRepository.save(loan);

        List<Payment> paymentList = loanRepository.findPayments(LocalDate.now(),
                List.of(PaymentStatus.GENERATED, PaymentStatus.FAILED));

        assertEquals(2, paymentList.size());
    }

    @Test
    public void findPayments_twoPaymentsOneWithDateInTheFuture_listWithOneElement() {
        var loan = new Loan();
        loan.setLoanDate(LocalDate.now());
        loan.setIdCustomer(100L);
        loan.setRate(10D);
        loan.setSubTotal(2_000D);
        loan.setIsNextPeriod(false);
        loan.setInstallmentAmount(0D);

        var payment = new Payment();
        payment.setAmount(1_000D);
        payment.setStatus(PaymentStatus.GENERATED);
        payment.setPaymentDate(LocalDate.now());

        var payment2 = new Payment();
        payment2.setAmount(1_000D);
        payment2.setStatus(PaymentStatus.GENERATED);
        payment2.setPaymentDate(LocalDate.now().plusDays(1));

        loan.getPayments().add(payment);
        loan.getPayments().add(payment2);

        this.loanRepository.save(loan);

        List<Payment> paymentList = loanRepository.findPayments(LocalDate.now(),
                List.of(PaymentStatus.GENERATED));

        assertEquals(1, paymentList.size());
    }

    @Test
    @Transactional
    public void updatePaymentStatus_paymentWithNewStatus_recordsAffected() {
        var loan = new Loan();
        loan.setLoanDate(LocalDate.now());
        loan.setIdCustomer(100L);
        loan.setRate(10D);
        loan.setSubTotal(2_000D);
        loan.setIsNextPeriod(false);
        loan.setInstallmentAmount(0D);

        var payment = new Payment();
        payment.setAmount(1_000D);
        payment.setStatus(PaymentStatus.GENERATED);
        payment.setPaymentDate(LocalDate.now());

        loan.getPayments().add(payment);

        this.loanRepository.save(loan);

        var result = loanRepository.updatePaymentStatus(PaymentStatus.PROCESSED, payment.getId());

        assertEquals(1, result);
    }

    @Test
    public void findPaymentsByCustomer_twoPaymentsByOneUser_listWithTwoElement() {
        var loan = new Loan();
        loan.setLoanDate(LocalDate.now());
        loan.setIdCustomer(100L);
        loan.setRate(10D);
        loan.setSubTotal(2_000D);
        loan.setIsNextPeriod(false);
        loan.setInstallmentAmount(0D);

        var payment = new Payment();
        payment.setAmount(1_000D);
        payment.setStatus(PaymentStatus.GENERATED);
        payment.setPaymentDate(LocalDate.now());

        var payment2 = new Payment();
        payment2.setAmount(1_000D);
        payment2.setStatus(PaymentStatus.FAILED);
        payment2.setPaymentDate(LocalDate.now().plusDays(15));

        loan.getPayments().add(payment);
        loan.getPayments().add(payment2);

        this.loanRepository.save(loan);

        List<Payment> paymentList = loanRepository.findPayments(100L,
                List.of(PaymentStatus.GENERATED, PaymentStatus.FAILED));

        assertEquals(2, paymentList.size());
    }
}
