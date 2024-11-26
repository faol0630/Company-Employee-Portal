package com.faol.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecurityApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);

		if (args.length < 1){
			System.out.println("parameter not found");
		}
		//for test:
		try{
			String params = args[0];
			SpringApplication.run(SecurityApplication.class, params);
		}catch (ArrayIndexOutOfBoundsException e){
			System.out.println(e.getMessage()); //Index 0 out of bounds for length 0
		}

	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Security proyect successfully started");


	}

}
