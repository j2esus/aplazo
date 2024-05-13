package com.aplazo.loan.services;

import com.aplazo.loan.clients.*;
import com.aplazo.loan.dtos.LoanResponseDTO;
import com.aplazo.loan.dtos.LoanRequestDTO;
import com.aplazo.loan.entities.Loan;
import com.aplazo.loan.entities.Payment;
import com.aplazo.loan.exceptions.ErrorPaymentException;
import com.aplazo.loan.exceptions.ErrorStatusException;
import com.aplazo.loan.repositories.LoanRepository;
import com.aplazo.loan.utils.PaymentStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class LoanService {
    private final LoanRepository loanRepository;
    private final CustomerClient customerClient;
    private final SchemeClient schemeClient;
    private final CreditLineClient creditLineClient;

    public LoanResponseDTO create(LoanRequestDTO schemeRequestDTO) {
        checkIfCustomerExists(schemeRequestDTO.getIdCustomer());
        var scheme = findScheme(schemeRequestDTO.getIdScheme());

        checkIfCustomerIsTheSame(scheme, schemeRequestDTO);

        var loan = new Loan(scheme, schemeRequestDTO.getAmount());
        loanRepository.save(loan);

        return new LoanResponseDTO(loan.getId(), "SUCCESS");
    }

    public List<Loan> findByIdCustomer(Long idCustomer) {
        return loanRepository.findByIdCustomer(idCustomer);
    }

    @Transactional
    public void processPayments() {
        LocalDate date = LocalDate.now();

        List<Payment> payments = this.loanRepository.findPayments(date,
                List.of(PaymentStatus.GENERATED, PaymentStatus.FAILED));

        payments.stream().forEach(payment -> {
            try {
                new ProcessPaymentFacade().processPayment(payment);
                loanRepository.updatePaymentStatus(PaymentStatus.PROCESSED, payment.getId());
            }catch(ErrorPaymentException e) {
                loanRepository.updatePaymentStatus(PaymentStatus.FAILED, payment.getId());
            }
        });
    }

    @Transactional(readOnly = true)
    public Double availableCredit(Long idCustomer) {
        checkIfCustomerExists(idCustomer);

        var paymentsNotProceeded = loanRepository.findPayments(idCustomer, List.of(PaymentStatus.GENERATED, PaymentStatus.FAILED));
        var amountNotProceeded = paymentsNotProceeded.stream().mapToDouble(i -> i.getAmount()).sum();

        var creditLine = findCreditLine(idCustomer);

        return creditLine.getAmount() - amountNotProceeded;
    }

    private void checkIfCustomerExists(Long idCustomer) {
        var customer = customerClient.findById(idCustomer);

        if(Objects.isNull(customer)) {
            throw new ErrorStatusException("Customer with id " + idCustomer + " does not exist.");
        }
    }

    private SchemeResponse findScheme(Long idScheme) {
        var scheme = schemeClient.findById(idScheme);

        if(Objects.isNull(scheme)) {
            throw new ErrorStatusException("Scheme with id " + idScheme + " does not exist.");
        }

        return scheme;
    }

    private void checkIfCustomerIsTheSame(SchemeResponse scheme, LoanRequestDTO schemeRequestDTO) {
        if(!scheme.getIdCustomer().equals(schemeRequestDTO.getIdCustomer())) {
            throw new ErrorStatusException("The customer id doest not equal with customer id from the scheme.");
        }
    }

    private CreditLineResponse findCreditLine(Long idCustomer) {
        var creditLine = this.creditLineClient.findByIdCustomer(idCustomer);
        if(Objects.isNull(creditLine)) {
            throw new ErrorStatusException("The customer with id "+idCustomer+" does not have a credit line.");
        }
        return creditLine;
    }
}
