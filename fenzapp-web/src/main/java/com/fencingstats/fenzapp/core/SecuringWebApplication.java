package com.fencingstats.fenzapp.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.fencingstats.fenzapp"})
@EntityScan(basePackages = {"com.fencingstats.fenzapp.model"})
@EnableJpaRepositories(basePackages = {"com.fencingstats.fenzapp.dao"})
public class SecuringWebApplication {

	public static void main(String[] args) throws Throwable {
		SpringApplication.run(SecuringWebApplication.class, args);
	}

}
