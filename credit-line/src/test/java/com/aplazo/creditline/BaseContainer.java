package com.aplazo.creditline;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=create-drop"})
public class BaseContainer {

    @Container
    @ServiceConnection
    protected static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.2");
}
