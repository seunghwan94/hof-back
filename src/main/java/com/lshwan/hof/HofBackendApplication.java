package com.lshwan.hof;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HofBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(HofBackendApplication.class, args);
	}
}
