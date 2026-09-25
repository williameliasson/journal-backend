package com.williameliasson.journal_backend.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateMoodRequest(@NotBlank String name) {
}
