package com.williameliasson.journal_backend.controllers;

import com.williameliasson.journal_backend.dtos.CreateJournalEntryRequest;
import com.williameliasson.journal_backend.dtos.JournalEntryResponse;
import com.williameliasson.journal_backend.models.JournalEntry;
import com.williameliasson.journal_backend.models.User;
import com.williameliasson.journal_backend.services.AuthService;
import com.williameliasson.journal_backend.services.JournalEntryService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api")
public class JournalEntryController {
    
    private final JournalEntryService journalEntryService;
    private final AuthService authService;

    JournalEntryController(JournalEntryService journalEntryService, AuthService authService) {
        this.journalEntryService = journalEntryService;
        this.authService = authService;
    }

    @GetMapping("/me/journalentries")
    public ResponseEntity<List<JournalEntryResponse>> getJournalEntries(Authentication authentication) {
        try{
            User user = authService.getUserByUsername(authentication.getName());
            List<JournalEntry> entries= journalEntryService.getJournalEntriesByUser(user);
            List<JournalEntryResponse> entryResponses = entries.stream()
                .map(entry -> 
                    new JournalEntryResponse(entry.getId(), user.getUsername(), entry.getTitle(), entry.getCreatedAt(), entry.getMood().getName())
                ).toList();
            return ResponseEntity.status(HttpStatus.OK).body(entryResponses);
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/me/journalentries")
    public ResponseEntity<String> createJournalEntry(@Valid @RequestBody CreateJournalEntryRequest createJournalEntryRequest, Authentication authentication) {
        try {
            User user = authService.getUserByUsername(authentication.getName());
            journalEntryService.createJournalEntry(createJournalEntryRequest, user);
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    
}
