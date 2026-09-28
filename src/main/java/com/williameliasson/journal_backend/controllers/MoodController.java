package com.williameliasson.journal_backend.controllers;

import com.williameliasson.journal_backend.services.MoodService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RestController;

import com.williameliasson.journal_backend.dtos.CreateMoodRequest;
import com.williameliasson.journal_backend.dtos.MoodResponse;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController 
@RequestMapping("/api/moods")
public class MoodController {
    private final MoodService moodService;

    MoodController(MoodService moodService) {
        this.moodService = moodService;
    }

    @PostMapping("")
    public ResponseEntity<String> createMood(@Valid @RequestBody CreateMoodRequest createMoodRequest) {
        try {
            moodService.createMood(createMoodRequest);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @GetMapping("")
    public List<MoodResponse> getAllMoods() {
        return moodService.getAllMoods().stream()
            .map(mood -> new MoodResponse(
                mood.getId(), mood.getName(), mood.getIcon()
            )).toList();
    }
    
}
