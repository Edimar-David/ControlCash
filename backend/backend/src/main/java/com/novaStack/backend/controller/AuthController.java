package com.novaStack.backend.controller;


import com.novaStack.backend.DTO.LoginRequestDTO;
import com.novaStack.backend.DTO.RegisterRequestDTO;
import com.novaStack.backend.DTO.UserResponseDTO;
import com.novaStack.backend.infra.security.TokenService;
import com.novaStack.backend.model.User;
import com.novaStack.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO dto){
        User user = this.repository.findByEmail(dto.email()).orElseThrow(() -> new RuntimeException("user not found"));
        if (passwordEncoder.matches(dto.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new UserResponseDTO(user.getName(), token));
        }
        return ResponseEntity.badRequest().body("user not found");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO dto){
        Optional<User> userOptional = this.repository.findByEmail(dto.email());
        if(userOptional.isEmpty()){
            User user = new User(dto.name(), dto.email(), passwordEncoder.encode(dto.password()));
            this.repository.save(user);
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new UserResponseDTO(user.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }
}


