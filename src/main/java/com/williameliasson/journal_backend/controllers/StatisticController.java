package com.williameliasson.journal_backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.williameliasson.journal_backend.dtos.StatisticsResponse;
import com.williameliasson.journal_backend.models.User;
import com.williameliasson.journal_backend.services.AuthService;
import com.williameliasson.journal_backend.services.StatisticService;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController 
@RequestMapping("/api")
public class StatisticController {
    private final StatisticService statisticService;
    private final AuthService authService;

    StatisticController(AuthService authService, StatisticService statisticService){
        this.authService = authService;
        this.statisticService = statisticService;
    }
    @GetMapping("/me/journalentries/statistics")
    public ResponseEntity<StatisticsResponse> getMyStatistics(@RequestParam(name="from") String fromString, @RequestParam(name="to") String toString, Authentication authentication) {
        try {
            User user = authService.getUserByUsername(authentication.getName());
            // src: https://www.geeksforgeeks.org/java/convert-string-to-localdate-in-java/
            LocalDate from = LocalDate.parse(fromString);
            LocalDate to = LocalDate.parse(toString);
            
            StatisticsResponse statisticsResponse = statisticService.getStatistics(from, to, user.getUsername());
            return ResponseEntity.ok().body(statisticsResponse);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }
    
}
