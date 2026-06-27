package com.novaStack.backend.service;

import com.novaStack.backend.dto.auth.LoginRequestDTO;
import com.novaStack.backend.dto.auth.RegisterRequestDTO;
import com.novaStack.backend.dto.auth.UserResponseDTO;
import com.novaStack.backend.infra.security.TokenService;
import com.novaStack.backend.model.User;
import com.novaStack.backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository repository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository repository, TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> login(LoginRequestDTO dto, HttpServletResponse response) {
        Optional<User> userOptional = this.repository.findByEmail(dto.email());
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        User user = userOptional.get();

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        return authenticateUser(response, user);
    }

    public ResponseEntity<?> register(RegisterRequestDTO dto, HttpServletResponse response) {
        Optional<User> userOptional = this.repository.findByEmail(dto.email());
        if(userOptional.isEmpty()){
            User user = new User(dto.name(), dto.email(), passwordEncoder.encode(dto.password()));
            this.repository.save(user);
            return authenticateUser(response, user);
        }
        return ResponseEntity.badRequest().build();
    }


    @NonNull
    private ResponseEntity<?> authenticateUser(HttpServletResponse response, User user) {
        String token = this.tokenService.generateToken(user);

        ResponseCookie cookie = ResponseCookie.from("access_token", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .sameSite("Lax")
                .maxAge(60 * 60 * 24)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok(new UserResponseDTO(user.getName()));
    }
}
