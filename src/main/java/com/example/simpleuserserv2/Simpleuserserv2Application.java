package com.example.simpleuserserv2;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc // Required for Swagger-UI
@SpringBootApplication
//@ComponentScan(basePackages = "com.example")
public class Simpleuserserv2Application {

	public static void main(String[] args) {
		SpringApplication.run(Simpleuserserv2Application.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}


}
