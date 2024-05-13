package com.aplazo.creditline.services;

import com.aplazo.creditline.clients.CustomerClient;
import com.aplazo.creditline.entities.CreditLine;
import com.aplazo.creditline.exceptions.ErrorStatusException;
import com.aplazo.creditline.repositories.CreditLineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CreditLineService {
    private final CreditLineRepository creditLineRepository;
    private final CustomerClient customerClient;

    public CreditLine create(CreditLine creditLine) {
        creditLine.setId(null);

        checkIfCustomerExists(creditLine.getIdCustomer());
        checkIfCreditLineExists(creditLine.getIdCustomer());

        return creditLineRepository.save(creditLine);
    }

    public Optional<CreditLine> findByIdCustomer(Long id) {
        checkIfCustomerExists(id);
        return this.creditLineRepository.findByIdCustomer(id);
    }

    private void checkIfCustomerExists(Long idCustomer) {
        var customer = customerClient.findById(idCustomer);

        if(Objects.isNull(customer)) {
            throw new ErrorStatusException("Customer with id " + idCustomer + " does not exist.");
        }
    }

    private void checkIfCreditLineExists(Long idCustomer) {
        var creditLineOptional = findByIdCustomer(idCustomer);

        if( creditLineOptional.isPresent()) {
            throw new ErrorStatusException("The Customer with id " + idCustomer + " already has a credit line.");
        }
    }
}
