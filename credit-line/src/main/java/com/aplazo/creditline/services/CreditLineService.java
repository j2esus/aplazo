package com.aplazo.creditline.services;

import com.aplazo.creditline.entities.CreditLine;
import com.aplazo.creditline.repositories.CreditLineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreditLineService {
    private final CreditLineRepository creditLineRepository;

    public CreditLine save(CreditLine creditLine){
        creditLine.setId(null);
        return creditLineRepository.save(creditLine);
    }
}
