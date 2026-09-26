package com.williameliasson.journal_backend.services;

import com.williameliasson.journal_backend.dtos.CreateJournalEntryRequest;
import com.williameliasson.journal_backend.models.JournalEntry;
import com.williameliasson.journal_backend.models.Mood;
import com.williameliasson.journal_backend.models.User;
import com.williameliasson.journal_backend.repositories.JournalEntryRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.stereotype.Service;

@Service 
public class JournalEntryService {
    private final MoodService moodService;
    private final JournalEntryRepository journalEntryRepository;

    JournalEntryService(JournalEntryRepository journalEntryRepository, MoodService moodService) {
        this.journalEntryRepository = journalEntryRepository;
        this.moodService = moodService;
    }

    public void createJournalEntry(CreateJournalEntryRequest createJournalEntryRequest, User user){    
        // get data
        Mood mood = moodService.getMoodById(createJournalEntryRequest.moodId());
        
        // construct the object
        JournalEntry journalEntry = new JournalEntry();
        journalEntry.setMood(mood);
        journalEntry.setUser(user);
        journalEntry.setTitle(createJournalEntryRequest.title());
        
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getJournalEntriesByUser(User user){
        return journalEntryRepository.findAllByUser(user);
    }

    public List<JournalEntry> getJournalEntriesByUserBetweenDates(User user, LocalDate fromDate, LocalDate toDate){
        Instant from = fromDate.atStartOfDay(ZoneId.of("Europe/Stockholm")).toInstant();
        Instant to = toDate.atStartOfDay(ZoneId.of("Europe/Stockholm")).toInstant();
        return journalEntryRepository.findAllByUserAndCreatedAtBetween(user, from, to);
    }
    
    // public List<JournalEntry> getAllJournalEntries(){
    //     return (List<JournalEntry>) journalEntryRepository.findAll();
    // }
}
