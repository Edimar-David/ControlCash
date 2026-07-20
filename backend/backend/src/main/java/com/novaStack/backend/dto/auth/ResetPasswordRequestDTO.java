package com.novaStack.backend.dto.auth;

public record ResetPasswordRequestDTO(
        String email,
        String code,
        String newPassword

) {
}
