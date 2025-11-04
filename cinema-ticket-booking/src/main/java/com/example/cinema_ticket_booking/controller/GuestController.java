package com.example.cinema_ticket_booking.controller;

import com.example.cinema_ticket_booking.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GuestController {

    private final SessionService sessionService;
    private final String applicationName;

    @Autowired
    public GuestController(SessionService sessionService, @Qualifier("appName") String applicationName){
        this.sessionService = sessionService;
        this.applicationName = applicationName;
    }

    @GetMapping("/")
    public String homePage(){
        return "redirect:/sessions";
    }

    @GetMapping("/sessions")
    public String listAllSessions(Model model){
        model.addAttribute("sessions", sessionService.findAll());
        model.addAttribute("appName", applicationName);
        return "guest/sessions-list";
    }
}
