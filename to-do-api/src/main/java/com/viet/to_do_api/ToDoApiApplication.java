package com.viet.to_do_api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import com.viet.to_do_api.mapper.AccountMapper;

@SpringBootApplication
@EnableAsync

public class ToDoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToDoApiApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(AccountMapper mapper) {
		return args -> {

		};
	}

}
