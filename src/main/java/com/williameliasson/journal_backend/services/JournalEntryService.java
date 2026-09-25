package com.williameliasson.journal_backend.services;

import com.williameliasson.journal_backend.models.JournalEntry;
import com.williameliasson.journal_backend.models.User;
import com.williameliasson.journal_backend.repositories.JournalEntryRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service 
public class JournalEntryService {
    private final JournalEntryRepository journalEntryRepository;

    JournalEntryService(JournalEntryRepository journalEntryRepository) {
        this.journalEntryRepository = journalEntryRepository;
    }

    public void createJournalEntry(){
        // construct the object
        JournalEntry journalEntry = new JournalEntry();
        
        // validate fields

        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getJournalEntriesByUser(User user){
        return journalEntryRepository.findAllByUser(user);
    }

    // remove this later! just for testing!
    public List<JournalEntry> getAllJournalEntries(){
        return (List<JournalEntry>) journalEntryRepository.findAll(); // har aldrig castat så förr men provar
    }
}
