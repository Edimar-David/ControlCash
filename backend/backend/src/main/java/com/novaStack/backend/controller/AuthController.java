package com.novaStack.backend.controller;


import com.novaStack.backend.dto.auth.LoginRequestDTO;
import com.novaStack.backend.dto.auth.RegisterRequestDTO;
import com.novaStack.backend.dto.auth.UserResponseDTO;
import com.novaStack.backend.infra.security.TokenService;
import com.novaStack.backend.model.User;
import com.novaStack.backend.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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

    private UserRepository repository;
    private PasswordEncoder passwordEncoder;
    private TokenService tokenService;

    public AuthController(UserRepository repository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO dto, HttpServletResponse response){
        Optional<User> userOptional = this.repository.findByEmail(dto.email());
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
        User user = userOptional.get();

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }

        return authenticateUser(response, user);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO dto, HttpServletResponse response){
        Optional<User> userOptional = this.repository.findByEmail(dto.email());
        if(userOptional.isEmpty()){
            User user = new User(dto.name(), dto.email(), passwordEncoder.encode(dto.password()));
            this.repository.save(user);
            return authenticateUser(response, user);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {

        Cookie cookie = new Cookie("access_token", "");
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);

        return ResponseEntity.ok().build();
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


