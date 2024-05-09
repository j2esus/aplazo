package com.aplazo.creditline.services;

import com.aplazo.creditline.clients.CustomerClient;
import com.aplazo.creditline.clients.CustomerResponse;
import com.aplazo.creditline.entities.CreditLine;
import com.aplazo.creditline.repositories.CreditLineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CreditLineService {
    private final CreditLineRepository creditLineRepository;
    private final CustomerClient customerClient;

    public Map<String, String> save(CreditLine creditLine) {
        creditLine.setId(null);
        CustomerResponse customer = customerClient.findById(creditLine.getIdCustomer());

        if(Objects.isNull(customer)) {
            return Map.of("status", "FAILURE", "description",
                    "Customer with id " + creditLine.getIdCustomer() + "does not exist");
        }
        creditLineRepository.save(creditLine);

        return Map.of("status", "SUCCESS",
                "description", "Credit line was created successfully.");
    }
}
