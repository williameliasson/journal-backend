package com.williameliasson.journal_backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
    @NotBlank 
    String username, 
    
    @Size(min=8)
    String password
) {
}
