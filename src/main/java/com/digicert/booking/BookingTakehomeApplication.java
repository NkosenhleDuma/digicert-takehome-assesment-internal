package com.digicert.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookingTakehomeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookingTakehomeApplication.class, args);
    }
}