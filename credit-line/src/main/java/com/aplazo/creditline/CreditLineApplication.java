package com.aplazo.creditline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CreditLineApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditLineApplication.class, args);
	}
}
