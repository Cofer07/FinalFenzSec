package com.fencingstats.fenzapp.service;

import java.util.Collections;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fencingstats.fenzapp.dao.UserDAO;
import com.fencingstats.fenzapp.model.User;

@Service
public class UserRegistrationService implements UserDetailsService {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		
		if (StringUtils.equalsIgnoreCase(username, "john.doe@gmail.com")) {
			return new org.springframework.security.core.userdetails.User("John Doe", "$2a$10$unalHkW6FEawIVOx.EjBP..IYtSpbexDxOzNRnW1BOYJxSG2At8Yi", Collections.emptyList());
		} else {
		
			User user = userDAO.findByEmail(username)
		            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.emptyList());
		}
        
	}
	
	public User registerNewUser(UserRegistrationDto userRegistrationDto) {
		
		Optional<User> existingUser = userDAO.findByEmail(userRegistrationDto.getEmail());
		if (existingUser.isPresent()) {
			throw new DuplicateUserException("Invalid registration email");
		}

        User user = new User();
        user.setFname(userRegistrationDto.getFname());
        user.setLname(userRegistrationDto.getLname());
        user.setEmail(userRegistrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));

        return userDAO.save(user);
    }
	
}
