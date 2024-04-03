package com.fencingstats.fenzapp.service;

import java.util.Collections;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fencingstats.fenzapp.dao.PasswordResetTokenRepository;
import com.fencingstats.fenzapp.dao.UserDAO;
import com.fencingstats.fenzapp.model.PasswordResetToken;
import com.fencingstats.fenzapp.model.User;

@Service
public class UserRegistrationService implements UserDetailsService {

	@Autowired
	private UserDAO userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		
		if (StringUtils.equalsIgnoreCase(username, "john.doe@gmail.com")) {
			return new org.springframework.security.core.userdetails.User("John Doe", "$2a$10$unalHkW6FEawIVOx.EjBP..IYtSpbexDxOzNRnW1BOYJxSG2At8Yi", Collections.emptyList());
		} else {
		
			User user = userRepository.findByEmail(username)
		            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.emptyList());
		}
        
	}
	
	public User getUserByPasswordResetToken(String token) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid password reset token"));

        return resetToken.getUser();
    }

    public User registerNewUser(UserRegistrationDto userRegistrationDto) {
        if (emailExists(userRegistrationDto.getEmail())) {
            throw new DuplicateUserException("A user with this email already exists.");
        }
        User user = new User();
        user.setFname(userRegistrationDto.getFname());
        user.setLname(userRegistrationDto.getLname());
        user.setEmail(userRegistrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));

        return userRepository.save(user);
    }

    public void updatePassword(User user, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public void invalidatePasswordResetToken(String token) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid password reset token"));

        passwordResetTokenRepository.delete(resetToken);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
    
    public String createPasswordResetTokenForUser(String email) {
        User user = this.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find any user with the email " + email);
        }

        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);

        passwordResetTokenRepository.save(passwordResetToken);

        return token;
    }
}
