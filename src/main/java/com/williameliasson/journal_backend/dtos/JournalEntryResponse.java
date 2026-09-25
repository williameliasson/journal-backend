package com.williameliasson.journal_backend.dtos;

import java.time.Instant;

public record JournalEntryResponse(Integer id, String username, String title, Instant createdAt, String moodName) {
}
