package com.fencingstats.fenzapp.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fencingstats.fenzapp.model.User;

@Repository
public interface UserDAO extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String username);
	
	Optional<User> findByEmail(String email);
}
