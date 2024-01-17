package com.ondot.ondot_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class OndotBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(OndotBackApplication.class, args);
	}

}
