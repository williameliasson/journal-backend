package com.williameliasson.journal_backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateMoodRequest(@NotBlank String name, @NotBlank @Size(min=1, max=1) String icon) {
}
