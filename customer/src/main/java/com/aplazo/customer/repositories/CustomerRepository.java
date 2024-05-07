package com.aplazo.customer.repositories;

import java.math.BigInteger;
import com.aplazo.customer.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, BigInteger> {

}
