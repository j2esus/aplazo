package com.aplazo.scheme.controllers;

import com.aplazo.scheme.dtos.SchemaResponseDTO;
import com.aplazo.scheme.dtos.SchemeRequestDTO;
import com.aplazo.scheme.entities.Scheme;
import com.aplazo.scheme.services.SchemeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schemes")
@AllArgsConstructor
public class SchemeController {
    private final SchemeService schemeService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public List<SchemaResponseDTO> create(@RequestBody SchemeRequestDTO schemeRequest) {
        return schemeService.create(schemeRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Scheme> findById(@PathVariable Long id) {
        var scheme = schemeService.findById(id);
        return scheme.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }
}
