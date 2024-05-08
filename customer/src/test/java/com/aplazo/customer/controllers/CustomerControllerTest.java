package com.aplazo.customer.controllers;

import com.aplazo.customer.BaseRestTest;
import com.aplazo.customer.entities.Customer;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import static io.restassured.RestAssured.given;

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
public class CustomerControllerTest extends BaseRestTest {

    @Test
    public void findCustomerById_nonExistentCustomer_httpError204() {
        Long id = 1L;
        given()
                .pathParam("id", id)
                .when()
                .get("/customers/{id}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void findCustomerById_existentCustomer_customerIsReturnedHttp200() {
        var newCustomer = new Customer();
        newCustomer.setName("John Wick");

        var resultCustomer = customerRepository.save(newCustomer);

        given()
                .pathParam("id", resultCustomer.getId())
                .when()
                .get("/customers/{id}")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void createCustomer_newCustomer_customerIsCreatedHttp201() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"name\": \"John Wick\" }")
                .when()
                .post("/customers")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(ContentType.JSON);
    }
}
