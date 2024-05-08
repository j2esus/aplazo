package com.aplazo.creditline.controllers;

import com.aplazo.creditline.BaseRestTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import static io.restassured.RestAssured.given;

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
public class CreditLineControllerTest extends BaseRestTest {

    @Test
    public void createCreditLine_newCreditLine_creditLineIsCreatedHttp201() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"idCustomer\": 100, \"amount\": 35000 }")
                .when()
                .post("/credit-line")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(ContentType.JSON);
    }
}
