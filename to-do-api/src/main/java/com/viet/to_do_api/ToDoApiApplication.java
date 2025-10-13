package com.viet.to_do_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableAsync
@OpenAPIDefinition(info = @Info(title = "To do app api", contact = @Contact(name = "Nguyen Dang Viet", email = "viet.ngdnag.dev@gmail.com"), version = "v1.0.1"))
public class ToDoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToDoApiApplication.class, args);
	}

}
