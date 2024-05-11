package com.aplazo.scheme.services;

import com.aplazo.scheme.clients.CustomerClient;
import com.aplazo.scheme.dtos.SchemaResponseDTO;
import com.aplazo.scheme.dtos.SchemeRequestDTO;
import com.aplazo.scheme.entities.Scheme;
import com.aplazo.scheme.exceptions.ErrorStatusException;
import com.aplazo.scheme.repositories.SchemeRepository;
import com.aplazo.scheme.utils.Constants;
import com.aplazo.scheme.utils.SchemeDefault;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class SchemeService {
    private final SchemeRepository schemeRepository;
    private final CustomerClient customerClient;

    public List<SchemaResponseDTO> create(SchemeRequestDTO schemeRequestDTO) {
        List<SchemaResponseDTO> schemaResponseDTOS = new ArrayList<>();

        checkIfCustomerExists(schemeRequestDTO.getIdCustomer());

        // TODO check is the user is approved for the loan.
        //  1. Only 5 loans in progress in the last 30 days
        //  2. only if the credit line is enough for the new loan

        Arrays.stream(SchemeDefault.values())
                .forEach( schemeDefault -> {
                    var schemeResponseDTO = createScheme(schemeRequestDTO, schemeDefault, false);
                    schemaResponseDTOS.add(schemeResponseDTO);

                    //TODO creating the schemes with next period as true according to loans api
                });

        return schemaResponseDTOS;
    }

    public Optional<Scheme> findById(Long id) {
        return this.schemeRepository.findById(id);
    }

    private void checkIfCustomerExists(Long idCustomer) {
        var customer = customerClient.findById(idCustomer);

        if(Objects.isNull(customer)) {
            throw new ErrorStatusException("Customer with id " + idCustomer + " does not exist.");
        }
    }

    private SchemaResponseDTO createScheme(SchemeRequestDTO schemeRequestDTO,
                                           SchemeDefault schemeDefault, boolean isNextPeriod) {
        var scheme = new Scheme();
        scheme.setSubTotal(schemeRequestDTO.getAmount());
        scheme.setIdCustomer(schemeRequestDTO.getIdCustomer());
        scheme.setInstallmentAmount(0D); // I don't know what value should be here.
        scheme.setRate(schemeDefault.getPercentage());
        scheme.setIsNextPeriod(isNextPeriod);
        schemeRepository.save(scheme);

        var schemeResponseDTO = new SchemaResponseDTO(scheme);
        schemeResponseDTO.setPaymentDates(getPaymentDates(schemeDefault, isNextPeriod));

        return schemeResponseDTO;
    }

    private Set<LocalDate> getPaymentDates(SchemeDefault schemeDefault, boolean startInNextPeriod) {
        Set<LocalDate> paymentDates = new HashSet<>();

        for(int i = 0; i < schemeDefault.getNumOfPayments(); i++) {
            long numOfDaysPerPayment = ( startInNextPeriod ? (i + 1) : i ) * Constants.NUM_OF_DAYS_PER_PAYMENT;

            paymentDates.add(LocalDate.now().plusDays(numOfDaysPerPayment));
        }

        return paymentDates;
    }
}
