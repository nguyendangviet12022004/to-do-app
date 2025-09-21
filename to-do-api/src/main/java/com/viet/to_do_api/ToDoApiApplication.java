package com.viet.to_do_api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import com.viet.to_do_api.dto.auth.RegisterRequest;
import com.viet.to_do_api.entity.Account;
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
			RegisterRequest request = RegisterRequest.builder()
					.email("test@example.com")
					.password("123456")
					.build();

			Account account = mapper.toAccount(request);

			System.out.println("Mapped Account:");
			System.out.println("Email: " + account.getEmail());
			System.out.println("Password: " + account.getPassword());
			System.out.println("isActive: " + account.isActive());
			System.out.println("Authorities: " + account.getAuthorities());
			System.out.println("ID: " + account.getId());

		};
	}

}
