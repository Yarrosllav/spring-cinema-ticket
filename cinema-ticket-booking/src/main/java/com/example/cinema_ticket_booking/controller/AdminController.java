package com.example.cinema_ticket_booking.controller;

import com.example.cinema_ticket_booking.model.Session;
import com.example.cinema_ticket_booking.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final SessionService sessionService;
    private final String applicationName;

    @Autowired
    public AdminController(SessionService sessionService, @Qualifier("appName") String applicationName){
        this.sessionService = sessionService;
        this.applicationName = applicationName;
    }

    @GetMapping("/sessions")
    public String adminPanel(Model model){
        model.addAttribute("sessions", sessionService.findAll());
        model.addAttribute("appName", applicationName);
        return "admin/admin-panel";
    }

    @GetMapping("/sessions/new")
    public String showCreateForm(Model model){
        model.addAttribute("movieSession", new Session());
        return "admin/session-form";
    }

    @PostMapping("/sessions")
    public String createOrUpdateSession(@ModelAttribute Session session){
        sessionService.save(session);
        return "redirect:/admin/sessions";
    }

    @GetMapping("/sessions/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model){
        model.addAttribute("movieSession", sessionService.findById(id));
        return "admin/session-form";
    }

    @GetMapping("/sessions/delete/{id}")
    public String deleteSession(@PathVariable Long id, Model model){
        sessionService.deleteById(id);
        return "redirect:/admin/sessions";
    }

}
