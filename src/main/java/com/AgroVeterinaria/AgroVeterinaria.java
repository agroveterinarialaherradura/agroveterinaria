package com.AgroVeterinaria;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AgroVeterinaria {
    public static void main(String[] args) {
        SpringApplication.run(AgroVeterinaria.class, args);
    }
}