package com.digicert.booking.config;

import com.digicert.booking.service.BookingService;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(BookingService .class);
    }
}