package com.petexpress.israel;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Swagger PetExpress", version = "1.0.0", description = "API desenvolvida para o backend PetExpress - Unimar"))
public class IsraelApplication {

	public static void main(String[] args) {
		SpringApplication.run(IsraelApplication.class, args);
	}

}
