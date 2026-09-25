package com.williameliasson.journal_backend.services;

import com.williameliasson.journal_backend.repositories.UserRepository;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.williameliasson.journal_backend.dtos.LoginRequest;
import com.williameliasson.journal_backend.dtos.RegisterRequest;
import com.williameliasson.journal_backend.models.User;

@Service 
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public Authentication register(RegisterRequest registerRequest){
        // validate
        if (!registerRequest.password().equals(registerRequest.matchingPassword())){
            throw new IllegalArgumentException("Passwords do not match");
        }
        // check if username is taken
        Optional<User> existingUser = userRepository.findByUsername(registerRequest.username());
        if (existingUser.isPresent()) throw new IllegalArgumentException("Username taken");
        
        // save new user
        User user = new User();
        user.setUsername(registerRequest.username());
        user.setPassword(passwordEncoder.encode(registerRequest.password()));
        userRepository.save(user);

        // login the user immediately, and return the session
        return login(new LoginRequest(registerRequest.username(), registerRequest.password()));
    }

    public Authentication login(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
        );

        // SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}
