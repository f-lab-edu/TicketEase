package com.TicketEase.TE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
public class TeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeApplication.class, args);
	}

}
