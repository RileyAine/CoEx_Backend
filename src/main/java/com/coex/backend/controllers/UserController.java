package com.coex.backend.controllers;

import java.util.Date;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coex.backend.BackendApplication;
import com.coex.backend.model.User;
import com.coex.backend.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {
	private static final Logger LOGGER = LogManager.getLogger(BackendApplication.class);
    
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
	public Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable String id) {
	    Optional<User> userOptional = userRepository.findById(id);
	    if (userOptional.isPresent()) {
	        return ResponseEntity.ok(userOptional.get());
	    } else {
			ResponseEntity<User> responseEntity = ResponseEntity.notFound().build();
			LOGGER.info(responseEntity);
			LOGGER.info(id);
	        return responseEntity;
	    }
	}
	
	@PostMapping
	public User createUser(@Valid @RequestBody User user) {
		return userRepository.save(user);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable String id, @Valid @RequestBody User updatedUser) {
		Optional<User> userOptional = userRepository.findById(id);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			user.setAccessLevel(updatedUser.getAccessLevel());
			user.setEmail(updatedUser.getEmail());
			user.setFirstName(updatedUser.getFirstName());
			user.setIsVerified(updatedUser.getIsVerified());
			user.setLastName(updatedUser.getLastName());
			user.setPassword(updatedUser.getPassword());
			user.setUpdatedAt(new Date());
			userRepository.save(user);
			return ResponseEntity.ok(user);
		} else {
			ResponseEntity<User> responseEntity = ResponseEntity.notFound().build();
			LOGGER.info(responseEntity);
			LOGGER.info(id);
			LOGGER.info(updatedUser);
			return responseEntity;
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser (@PathVariable String id) {
		if (userRepository.existsById(id)) {
			userRepository.deleteById(id);
			return ResponseEntity.ok().build();
		} else {
			ResponseEntity<Void> responseEntity = ResponseEntity.notFound().build();
			LOGGER.info(responseEntity);
			LOGGER.info(id);
			return responseEntity;
		}
	}
}