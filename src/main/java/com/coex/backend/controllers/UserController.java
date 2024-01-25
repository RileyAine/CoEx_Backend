package com.coex.backend.controllers;

import java.util.Date;
import java.util.List;
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
	@Autowired
	private static final Logger LOGGER = LogManager.getLogger(BackendApplication.class);
    
	//@Autowired
	//private elasticsearchOperations elasticsearchOperations;
	
	@Autowired
	private UserRepository userRepository;
	
	//@Autowired
	//RestClient restClient;  
	
	//public UserController(ElasticsearchOperations elasticsearchOperations) { 
	//	this.elasticsearchOperations = elasticsearchOperations;
	//}
	
	@GetMapping
	public ResponseEntity<Iterable<User>> getAllUsers() {
		return ResponseEntity.ok(userRepository.findAll());	
	}
	
	@GetMapping("/{idOrEmail}")
	public ResponseEntity<User> getUserById(@PathVariable String idOrEmail) {
	    Optional<User> userOptional = userRepository.existsById(idOrEmail) ? userRepository.findById(idOrEmail) : userRepository.findByEmail(idOrEmail);
	    if (userOptional.isPresent()) {
	        return ResponseEntity.ok(userOptional.get());
	    } else {
			ResponseEntity<User> responseEntity = ResponseEntity.notFound().build();
			LOGGER.info(responseEntity);
			LOGGER.info(idOrEmail);
	        return responseEntity;
	    }
	}
	
	@PostMapping
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
		if (existingUser.isEmpty()) {
			return ResponseEntity.ok(userRepository.save(user));			
		} else {
			LOGGER.info("CreateUser found duplicate: <request>, <list found>:");
			LOGGER.info(user);
			LOGGER.info(existingUser);
			return ResponseEntity.badRequest().build();
		}
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