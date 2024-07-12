package com.example.Mediateca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.Mediateca.*")
@EntityScan("com.example.Mediateca.*")
@EnableAsync
@EnableEncryptableProperties
public class MediatecaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediatecaApplication.class, args);
	}

}
