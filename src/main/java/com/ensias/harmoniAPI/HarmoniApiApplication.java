package com.ensias.harmoniAPI;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;




@SpringBootApplication
public class HarmoniApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HarmoniApiApplication.class, args);
	}
	
	@Bean
    CommandLineRunner commandLineRunner() {
        return args -> {

        };
    }

}
