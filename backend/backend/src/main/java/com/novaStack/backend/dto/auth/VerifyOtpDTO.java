package com.novaStack.backend.dto.auth;

public record VerifyOtpDTO(
        String email,
        String code
) {
}
