package com.aplazo.scheme.controllers;

import com.aplazo.scheme.BaseRestTest;
import com.aplazo.scheme.entities.Scheme;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
public class SchemeControllerTest extends BaseRestTest {
    @Test
    public void findSchemeById_nonExistentScheme_httpError204() {
        Long id = 1L;
        given()
                .pathParam("id", id)
                .when()
                .get("/schemes/{id}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void findSchemeById_existentScheme_schemeIsReturnedHttp200() {
        var scheme = new Scheme();
        scheme.setRate(10.0);
        scheme.setIdCustomer(100L);
        scheme.setSubTotal(5_000.0);
        scheme.setIsNextPeriod(false);

        var resultScheme = schemeRepository.save(scheme);

        given()
                .pathParam("id", resultScheme.getId())
                .when()
                .get("/schemes/{id}")
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
