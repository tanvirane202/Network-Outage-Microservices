package com.networkoutage.Technician_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TechnicianServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechnicianServiceApplication.class, args);
		System.out.println("Technician service is running....");
	}

}
