package com.aplazo.scheme.services;

import com.aplazo.scheme.clients.CustomerClient;
import com.aplazo.scheme.dtos.SchemaResponse;
import com.aplazo.scheme.dtos.SchemeRequest;
import com.aplazo.scheme.entities.Scheme;
import com.aplazo.scheme.repositories.SchemeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SchemeService {
    private final SchemeRepository schemeRepository;
    private final CustomerClient customerClient;

    public List<SchemaResponse> paymentsSimulator(SchemeRequest schemeRequest) {
        var customer = customerClient.findById(schemeRequest.getIdCustomer());

        /*
        if(Objects.isNull(customer)) {
            return Map.of("status", "FAILURE", "description",
                    "Customer with id " + requestScheme.getIdCustomer() + " does not exist.");
        }
         */

        var basicScheme = new Scheme();
        basicScheme.setSubTotal(schemeRequest.getMount());
        basicScheme.setIdCustomer(schemeRequest.getIdCustomer());
        basicScheme.setInstallmentAmount(0D);
        basicScheme.setRate(10D);
        basicScheme.setIsNextPeriod(false);
        schemeRepository.save(basicScheme);

        SchemaResponse result = new SchemaResponse(basicScheme);
        // set values of payments

        return List.of(result);
    }

    public Optional<Scheme> findById(Long id) {
        return this.schemeRepository.findById(id);
    }
}
