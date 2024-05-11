package com.aplazo.scheme.repositories;

import com.aplazo.scheme.entities.Scheme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchemeRepository extends JpaRepository<Scheme, Long> {
    List<Scheme> findByIdCustomer(Long idCustomer);
}
