package com.williameliasson.journal_backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateJournalEntryRequest(
    @NotBlank String title,
    @NotNull  Integer moodId
) {   
}
