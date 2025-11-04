package com.example.cinema_ticket_booking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public String appName(){
        return "Cinema Ticket Booking System";
    }

}
