package com.novaStack.backend.DTO;

public record VerifyOtpDTO(
        String email,
        String code
) {
}
