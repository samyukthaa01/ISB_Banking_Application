package com.restAPI.banking_app;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "The ISB Banking Application",
				description = "Backend REST Apis for ISB Bank",
				version = "v.1.0",
				contact = @Contact(
						name = "Samyuktha Mohan",
						email = "samyuktham-wm22@student.tarc.edu.my",
						url = "https://github.com/samyukthaa01/ISB_Banking_Application.git"
				),
				license = @License(
						name = "Samyuktha",
						url = "https://github.com/samyukthaa01/ISB_Banking_Application.git"
				)

		),
		externalDocs = @ExternalDocumentation(
				description = "The ISB Banking Application Documentation",
				url = "https://github.com/samyukthaa01/ISB_Banking_Application.git"
		)
)

public class BankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingApplication.class, args);
	}

}
