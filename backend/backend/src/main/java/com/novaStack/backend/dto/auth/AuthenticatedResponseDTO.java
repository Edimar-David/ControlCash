package com.novaStack.backend.dto.auth;

public record AuthenticatedResponseDTO(
        Long id,
        String name,
        String email
) {
}
