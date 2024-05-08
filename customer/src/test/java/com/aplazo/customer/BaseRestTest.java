package com.aplazo.customer;

import com.aplazo.customer.repositories.CustomerRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;

public class BaseRestTest extends BaseContainer {
    @LocalServerPort
    private Integer port;
    @Autowired
    protected CustomerRepository customerRepository;
    private static final String PATH = "http://localhost:";

    @BeforeEach
    public void before() {
        RestAssured.baseURI = PATH + port;
        customerRepository.deleteAll();
    }
}
