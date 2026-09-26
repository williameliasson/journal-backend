package com.williameliasson.journal_backend.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.williameliasson.journal_backend.models.JournalEntry;
import com.williameliasson.journal_backend.models.User;

public interface JournalEntryRepository extends CrudRepository<JournalEntry, Integer> {
    List<JournalEntry> findAllByUser(User user);
    List<JournalEntry> findAllByUserCreatedAtBetween(User user, LocalDate from, LocalDate to);
}
