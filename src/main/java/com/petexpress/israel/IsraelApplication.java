package com.petexpress.israel;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
		title = "Swagger PetExpress",
		version = "1.0.0",
		description = "API developed for the PetExpress backend - Unimar",
		contact = @Contact(name = "Israel Santos", email = "devisrael560@gmail.com")
))
public class IsraelApplication {

	public static void main(String[] args) {
		SpringApplication.run(IsraelApplication.class, args);
	}

}
