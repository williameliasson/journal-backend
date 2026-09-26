package com.williameliasson.journal_backend.repositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.williameliasson.journal_backend.models.JournalEntry;
import com.williameliasson.journal_backend.models.User;

public interface JournalEntryRepository extends CrudRepository<JournalEntry, Integer> {
    List<JournalEntry> findAllByUser(User user);
    List<JournalEntry> findAllByUserAndCreatedAtBetween(User user, Instant from, Instant to);
}
