package ru.edu.java.barbershop;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.edu.java.barbershop.api.service.jpa.JpaRecordAvailableTimeService;

@SpringBootApplication
@RequiredArgsConstructor
public class BarbershopApplication {
    private final JpaRecordAvailableTimeService service;

    public static void main(String[] args) {
        SpringApplication.run(BarbershopApplication.class, args);
    }

    @Bean
    public CommandLineRunner CommandLineRunnerBean() {
        return (args) -> {
            service.addTimesForBarbers();
        };
    }
}
