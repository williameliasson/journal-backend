package com.williameliasson.journal_backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.williameliasson.journal_backend.dtos.RegisterRequest;
import com.williameliasson.journal_backend.repositories.UserRepository;
import com.williameliasson.journal_backend.services.AuthService;

import jakarta.transaction.Transactional;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc 
@Testcontainers 
class AuthIntegrationTests {

	// src: https://www.youtube.com/watch?v=erp-7MCK5BU&t=1495s
	@Container 
	@ServiceConnection 
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:18");

	// Src: https://www.youtube.com/watch?v=m0LIXOP-Qrc
	@Autowired 
	MockMvc mockMvc;

	@Autowired 
	UserRepository userRepository;

	@Autowired 
	AuthService authService;

	@Autowired 
	ObjectMapper objectMapper;

	@Test
	void contextLoads() {
	}

	// helper method to make easier to read
	private MvcResult registerValidUser(String username) throws Exception{
		RegisterRequest registerRequest = new RegisterRequest(username, "password", "password");
		MvcResult mvcResult = mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest))) 
                .andExpect(status().isCreated())
                .andReturn();
		return mvcResult;	
	}

	@Test
	@Transactional 
	void userShouldBeCreatedWhenCorrectRequest() throws Exception{
		// create user using repository
		String username = "Bob";
		registerValidUser(username);
		assertTrue(userRepository.findByUsername(username).get().getUsername().equals(username));
		
	}
	
	@Test 
	@Transactional 
	void userShouldNotBeCreatedWhenPasswordsMismatch() throws Exception{
		// create user using repository
		String username = "Bob";
		String password = "password";
		String otherPassword = "otherpassword";
		RegisterRequest registerRequest = new RegisterRequest(username, password, otherPassword);
		MvcResult mvcResult = mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest))) 
                .andExpect(status().isBadRequest())
                .andReturn();		
		assertTrue(userRepository.findByUsername(username).isEmpty());
	}
}
