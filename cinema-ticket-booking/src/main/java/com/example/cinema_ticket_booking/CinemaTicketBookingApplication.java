package com.example.cinema_ticket_booking;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "Cinema Ticket Booking System API",
				description = "RESTful API для системи бронювання квитків у кінотеатрі. ",
				version = "1.0.0",
				contact = @Contact(
						name = "Yaroslav Los",
						url = "https://github.com/Yarrosllav"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.apache.org/licenses/LICENSE-2.0.html"
				)
		),
		servers = {
				@Server(
						url = "http://localhost:8080",
						description = "Local Development Server"
				)
		}
)

@SpringBootApplication
public class CinemaTicketBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinemaTicketBookingApplication.class, args);
	}

}
