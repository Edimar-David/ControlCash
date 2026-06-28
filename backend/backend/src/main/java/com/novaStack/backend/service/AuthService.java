package com.novaStack.backend.service;

import com.novaStack.backend.dto.auth.LoginRequestDTO;
import com.novaStack.backend.dto.auth.RegisterRequestDTO;
import com.novaStack.backend.dto.auth.UserResponseDTO;
import com.novaStack.backend.infra.security.TokenService;
import com.novaStack.backend.model.User;
import com.novaStack.backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository repository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final CookieService cookieService;

    public AuthService(UserRepository repository, TokenService tokenService, PasswordEncoder passwordEncoder, CookieService cookieService) {
        this.repository = repository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.cookieService = cookieService;
    }

    public ResponseEntity<?> login(LoginRequestDTO dto, HttpServletResponse httpServletResponse) {
        Optional<User> userOptional = this.repository.findByEmail(dto.email());
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        User user = userOptional.get();

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        return authenticateUser(user, httpServletResponse);
    }

    public ResponseEntity<?> register(RegisterRequestDTO dto, HttpServletResponse httpServletResponse) {
        Optional<User> userOptional = this.repository.findByEmail(dto.email());
        if(userOptional.isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }
            User user = new User(dto.name(), dto.email(), passwordEncoder.encode(dto.password()));
            this.repository.save(user);
            return authenticateUser(user, httpServletResponse);
    }


    @NonNull
    private ResponseEntity<?> authenticateUser(User user, HttpServletResponse httpServletResponse) {
        String token = this.tokenService.generateToken(user);
        cookieService.createCookie(token, httpServletResponse);

        return ResponseEntity.ok(new UserResponseDTO(user.getName()));
    }
}
