package com.fencingstats.fenzapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.fencingstats.fenzapp.service.DuplicateUserException;
import com.fencingstats.fenzapp.service.UserRegistrationDto;
import com.fencingstats.fenzapp.service.UserRegistrationService;

import jakarta.validation.Valid;

@Controller
public class RegistrationController {
	
	@Autowired
	private UserRegistrationService userService;
	
	@GetMapping("/signup")
	public String goToSignup(UserRegistrationDto userRegistrationDto) {
		return "signup_form";
	}

    @PostMapping("/register")
    public String processRegister(@Valid UserRegistrationDto userRegistrationDto, BindingResult result, Model model) {
    	if (result.hasErrors()){
            return "signup_form";
        }
        try {
            userService.registerNewUser(userRegistrationDto);
        } catch (DuplicateUserException e) {
            model.addAttribute("error", e.getMessage());
            return "signup_form";
        }
        model.addAttribute("registrationStatus", true);
        return "signup_form";
    }
	
}
