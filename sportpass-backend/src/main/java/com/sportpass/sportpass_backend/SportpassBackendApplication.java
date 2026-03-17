package com.sportpass.sportpass_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.sportpass.sportpass_backend")
public class SportpassBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportpassBackendApplication.class, args);
	}

}