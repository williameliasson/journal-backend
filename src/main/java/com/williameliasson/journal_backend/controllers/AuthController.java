package com.williameliasson.journal_backend.controllers;

import com.williameliasson.journal_backend.services.AuthService;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.RestController;

import com.williameliasson.journal_backend.dtos.LoginRequest;
import com.williameliasson.journal_backend.dtos.RegisterRequest;
import com.williameliasson.journal_backend.dtos.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController 
@CrossOrigin(origins="http://localhost:4200", allowCredentials = "true")
@RequestMapping("/api")
public class AuthController {

    private final SecurityContextRepository securityContextRepository;
    private final AuthService authService;
    

    AuthController(AuthService authService, SecurityContextRepository securityContextRepository) {
        this.authService = authService;
        this.securityContextRepository = securityContextRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest registerRequest, HttpServletRequest request, HttpServletResponse response) {    
        try {
            Authentication auth = authService.register(registerRequest);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
    
            context.setAuthentication(auth);
            SecurityContextHolder.setContext(context);
            securityContextRepository.saveContext(context, request, response);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public void login(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {        
        System.out.println(loginRequest.username());
        System.out.println(loginRequest.password());
        Authentication auth = authService.login(loginRequest);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);
    }
    
    @GetMapping("/me")
    public UserDTO getCurrentUsername(Authentication authentication) {
        if (authentication == null) return new UserDTO("");
        return new UserDTO(authentication.getName());
    }
}
