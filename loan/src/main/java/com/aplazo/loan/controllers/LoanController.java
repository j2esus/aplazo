package com.aplazo.loan.controllers;

import com.aplazo.loan.dtos.LoanResponseDTO;
import com.aplazo.loan.dtos.LoanRequestDTO;
import com.aplazo.loan.entities.Loan;
import com.aplazo.loan.services.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
@AllArgsConstructor
public class LoanController {
    private final LoanService loanService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public LoanResponseDTO create(@RequestBody LoanRequestDTO loanRequestDTO) {
        return loanService.create(loanRequestDTO);
    }

    @GetMapping("/customer/{id}")
    public List<Loan> findById(@PathVariable Long id) {
        return loanService.findByIdCustomer(id);
    }
}
