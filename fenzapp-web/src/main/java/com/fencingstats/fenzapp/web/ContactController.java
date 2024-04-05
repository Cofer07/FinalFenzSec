package com.fencingstats.fenzapp.web;

import com.fencingstats.fenzapp.model.ContactMessage;
import com.fencingstats.fenzapp.service.ContactMessageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactController {

    private final ContactMessageService contactMessageService;

    @Autowired
    public ContactController(ContactMessageService contactMessageService) {
        this.contactMessageService = contactMessageService;
    }

    @GetMapping("/contactus")
    public String showContactForm(Model model) {
        model.addAttribute("contactMessage", new ContactMessage());
        return "contactus";
    }

    @PostMapping("/contactus")
    public String submitContactMessage(@Valid ContactMessage contactMessage, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            // Form data has validation errors
            return "contactus";
        }
        try {
            contactMessageService.saveMessage(contactMessage);
            redirectAttributes.addFlashAttribute("success", "Your message has been sent successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "There was a problem with your submission: " + e.getMessage());
        }
        return "redirect:/contactus";
    }
}
