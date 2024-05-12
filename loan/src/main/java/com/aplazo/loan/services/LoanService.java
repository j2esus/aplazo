package com.aplazo.loan.services;

import com.aplazo.loan.clients.CustomerClient;
import com.aplazo.loan.clients.SchemeClient;
import com.aplazo.loan.clients.SchemeResponse;
import com.aplazo.loan.dtos.LoanResponseDTO;
import com.aplazo.loan.dtos.LoanRequestDTO;
import com.aplazo.loan.entities.Loan;
import com.aplazo.loan.exceptions.ErrorStatusException;
import com.aplazo.loan.repositories.LoanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class LoanService {
    private final LoanRepository loanRepository;
    private final CustomerClient customerClient;
    private final SchemeClient schemeClient;

    public LoanResponseDTO create(LoanRequestDTO schemeRequestDTO) {
        checkIfCustomerExists(schemeRequestDTO.getIdCustomer());
        var scheme = findScheme(schemeRequestDTO.getIdScheme());

        var loan = new Loan(scheme, schemeRequestDTO.getAmount());
        loanRepository.save(loan);

        return new LoanResponseDTO(loan.getId(), "SUCCESS");
    }

    public List<Loan> findByIdCustomer(Long idCustomer) {
        return loanRepository.findByIdCustomer(idCustomer);
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
}
