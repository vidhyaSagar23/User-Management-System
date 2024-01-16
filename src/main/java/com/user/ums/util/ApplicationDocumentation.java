package com.user.ums.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;


@Configuration
@OpenAPIDefinition
public class ApplicationDocumentation {

	@Bean
	OpenAPI openAPI() {
		return new OpenAPI().info(info());
	}

	Info info() {
		return new Info().title("User Management System API")
				.version("1.0v")
				.description("User Managent System is a RESTful API built using Spring boot and MySQL database").contact(contact());
	}

	Contact contact() {
		return new Contact().email("vidhyasagar@k01@gmail.com").name("Vidhya Sagar").url("http://bit.ly/sagarvp2k");
	}


}
