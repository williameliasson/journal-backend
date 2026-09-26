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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import com.williameliasson.journal_backend.dtos.RegisterRequest;
import com.williameliasson.journal_backend.models.User;
import com.williameliasson.journal_backend.repositories.UserRepository;
import com.williameliasson.journal_backend.services.AuthService;

import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc 
@Testcontainers 
class JournalBackendApplicationTests {

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

	@Test
	void userShouldBeCreated() throws Exception{
		// create user using repository
		String username = "Bob";
		RegisterRequest registerRequest = new RegisterRequest(username, "password", "password");
		MvcResult mvcResult = mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest))) 
                .andExpect(status().isCreated())
                .andReturn();		
		assertTrue(userRepository.findByUsername(username).get().getUsername().equals(username));
		
	}

}
