package com.coex.backend.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.coex.backend.model.User;
import com.coex.backend.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createUser_validData_success() throws Exception {
        User newUser = new User("USER", "test@test.com","false","test_password","Test","User");
        User savedUser = new User(newUser.getAccessLevel(), 
        		newUser.getEmail(), 
        		newUser.getIsVerified(), 
        		newUser.getPassword(), 
        		newUser.getFirstName(), 
        		newUser.getLastName());

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedUser.getId()))
                .andExpect(jsonPath("$.accessLevel").value(savedUser.getAccessLevel()))
                .andExpect(jsonPath("$.email").value(savedUser.getEmail()))
                .andExpect(jsonPath("$.isVerified").value(savedUser.getIsVerified()))
                .andExpect(jsonPath("$.password").value(savedUser.getPassword()))                                        
                .andExpect(jsonPath("$.firstName").value(savedUser.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(savedUser.getLastName()));
    }

    @Test
    public void createUser_invalidData_failure() throws Exception {
        User invalidUser = new User("null", "invalid-email", "", "123  ", "@!", "#@");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllUsers() throws Exception {
        User user1 = new User("USER", "test1@test.com","false","test_password1","Test1","User1");
        User user2 = new User("USER", "test2@test.com","false","test_password2","Test2","User2");
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                
                .andExpect(jsonPath("$[0].id").value(user1.getId()))
                .andExpect(jsonPath("$[0].accessLevel").value(user1.getAccessLevel()))
                .andExpect(jsonPath("$[0].email").value(user1.getEmail()))
                .andExpect(jsonPath("$[0].isVerified").value(user1.getIsVerified()))
                .andExpect(jsonPath("$[0].password").value(user1.getPassword()))                                        
                .andExpect(jsonPath("$[0].firstName").value(user1.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(user1.getLastName()))
                
		        .andExpect(jsonPath("$[1].id").value(user2.getId()))
		        .andExpect(jsonPath("$[1].accessLevel").value(user2.getAccessLevel()))
		        .andExpect(jsonPath("$[1].email").value(user2.getEmail()))
		        .andExpect(jsonPath("$[1].isVerified").value(user2.getIsVerified()))
		        .andExpect(jsonPath("$[1].password").value(user2.getPassword()))                                        
		        .andExpect(jsonPath("$[1].firstName").value(user2.getFirstName()))
		        .andExpect(jsonPath("$[1].lastName").value(user2.getLastName()));
    }

    @Test
    public void getUserById_found() throws Exception {
        User user = new User("USER", "test1@test.com","false","test_password1","Test1","User1");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        mockMvc.perform(get("/api/users/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.accessLevel").value(user.getAccessLevel()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.isVerified").value(user.getIsVerified()))
                .andExpect(jsonPath("$.password").value(user.getPassword()))                                        
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()));
    }

    @Test
    public void getUserById_notFound() throws Exception {
        String userId = UUID.randomUUID().toString();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isNotFound());
    }
}