package com.fencingstats.fenzapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fencingstats.fenzapp.model.User;
import com.fencingstats.fenzapp.service.UserRegistrationService;

@Controller
public class PasswordResetController {

	@Autowired
    private UserRegistrationService userService;
	
	@GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }


    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        User user = userService.findByEmail(email);
        if (user == null) {
            model.addAttribute("error", "No user found with the email " + email);
            return "forgot-password";
        }

//        userService.createPasswordResetTokenForUser(email);

        String token = userService.createPasswordResetTokenForUser(user.getEmail());
        String resetLink = "http://localhost:8080/reset-password?token=" + token;
        System.out.println("Reset link: " + resetLink);

        model.addAttribute("message", "A password reset link has been sent to " + email);
        return "forgot-password";
    }


    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "reset-password";
    }

    // Need to clean the new Password (verify that it matches correct standards)
    @PostMapping("/reset-password")
    public String handleResetPassword(@RequestParam("token") String token,
                                      @RequestParam("newPassword") String newPassword,
                                      @RequestParam("confirmPassword") String confirmPassword,
                                      Model model) {
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "reset-password";
        }

        try {
            // Retrieve the user associated with the reset token
            User user = userService.getUserByPasswordResetToken(token);
            if (user == null) {
                model.addAttribute("error", "Invalid token.");
                return "reset-password";
            }

            // Update the user's password
            userService.updatePassword(user, newPassword);

            // Optionally invalidate the token after successful password update
            userService.invalidatePasswordResetToken(token);

            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "Error resetting password.");
            return "reset-password";
        }
    }



}
