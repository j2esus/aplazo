package com.aplazo.creditline.controllers;

import com.aplazo.creditline.entities.CreditLine;
import com.aplazo.creditline.services.CreditLineService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/credit-line")
@AllArgsConstructor
public class CreditLineController {
    private final CreditLineService creditLineService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Map<String, String> create(@RequestBody CreditLine creditLine) {
        return creditLineService.save(creditLine);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditLine> findById(@PathVariable Long id) {
        var creditLine = creditLineService.findById(id);
        return creditLine.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }
}
