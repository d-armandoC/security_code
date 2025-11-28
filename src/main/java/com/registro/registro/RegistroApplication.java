package com.registro.registro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling; // Asegúrate de que esta línea esté presente

@SpringBootApplication
@EnableScheduling // Activa la programación de tareas
public class RegistroApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegistroApplication.class, args);
    }
}

