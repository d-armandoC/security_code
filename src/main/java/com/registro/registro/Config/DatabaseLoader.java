package com.registro.registro.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.util.Objects;

@Configuration
public class DatabaseLoader {

    @Bean
    public CommandLineRunner loadDatabase(JdbcTemplate jdbcTemplate) {
        System.out.println("Cargando las Carreras...");
        return args -> {
            Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM carrera", Integer.class);
            if (count == 0) {
                ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
                populator.addScript(new ClassPathResource("data.sql"));
                populator.execute(Objects.requireNonNull(jdbcTemplate.getDataSource()));
            }
        };
    }

}
