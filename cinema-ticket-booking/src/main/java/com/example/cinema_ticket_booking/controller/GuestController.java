package com.example.cinema_ticket_booking.controller;

import com.example.cinema_ticket_booking.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GuestController {

    private final SessionService sessionService;

    @Autowired
    public GuestController(SessionService sessionService){
        this.sessionService = sessionService;
    }

    @GetMapping("/")
    public String homePage(){
        return "redirect:/sessions";
    }

    @GetMapping("/sessions")
    public String listAllSessions(Model model){
        model.addAttribute("sessions", sessionService.findAll());
        return "guest/sessions-list";
    }


}
