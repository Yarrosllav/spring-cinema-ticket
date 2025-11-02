package com.example.cinema_ticket_booking.config;

import com.example.cinema_ticket_booking.model.Seat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

    @Bean
    public String appName(){
        return "Cinema Ticket Booking System";
    }

    @Bean
    @Scope("prototype")
    public Seat prototypeSeat(){
        return new Seat();
    }
}
