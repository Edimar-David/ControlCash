package com.novaStack.backend.service;

import com.novaStack.backend.dto.auth.AuthenticatedResponseDTO;
import com.novaStack.backend.dto.auth.LoginRequestDTO;
import com.novaStack.backend.dto.auth.RegisterRequestDTO;
import com.novaStack.backend.model.PendingUser;
import com.novaStack.backend.model.User;
import com.novaStack.backend.repository.PendingUserRepository;
import com.novaStack.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final PendingUserRepository pendingUserRepository;

    public AuthService(UserRepository repository, PasswordEncoder passwordEncoder, PendingUserRepository pendingUserRepository) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.pendingUserRepository = pendingUserRepository;
    }

    public AuthenticatedResponseDTO login(LoginRequestDTO dto) {
        Optional<User> userOptional = this.repository.findByEmail(dto.email());
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        User user = userOptional.get();

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        return convertToResponseDTO(user);

    }

    public void register(RegisterRequestDTO dto) {
        Optional<User> userOptional = this.repository.findByEmail(dto.email());
        if(userOptional.isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }
            PendingUser user = new PendingUser(dto.name(), dto.email(), passwordEncoder.encode(dto.password()));
            this.pendingUserRepository.save(user);
            return;
    }

    private AuthenticatedResponseDTO convertToResponseDTO(User user){

        return new AuthenticatedResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
