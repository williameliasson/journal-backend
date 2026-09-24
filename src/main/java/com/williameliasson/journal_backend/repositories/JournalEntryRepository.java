package com.williameliasson.journal_backend.repositories;

import org.springframework.data.repository.CrudRepository;

import com.williameliasson.journal_backend.models.JournalEntry;

public interface JournalEntryRepository extends CrudRepository<JournalEntry, Integer> {
}
