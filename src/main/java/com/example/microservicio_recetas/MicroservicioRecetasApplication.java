package com.example.microservicio_recetas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MicroservicioRecetasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioRecetasApplication.class, args);
	}
	
}
