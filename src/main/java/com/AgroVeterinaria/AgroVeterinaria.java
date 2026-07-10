package com.AgroVeterinaria;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;

@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
@Controller
@ComponentScan
@SpringBootApplication
@EnableAsync
public class AgroVeterinaria {

	public static void main(String[] args) {
		SpringApplication.run(AgroVeterinaria.class, args);
	}

}