package com.novaStack.backend.controller;


import com.novaStack.backend.dto.auth.*;
import com.novaStack.backend.infra.security.TokenService;
import com.novaStack.backend.service.AuthService;
import com.novaStack.backend.service.CookieService;
import com.novaStack.backend.service.OtpService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;
    private final TokenService tokenService;
    private final CookieService cookieService;
    private final OtpService otpService;

    public AuthController(AuthService service, TokenService tokenService, CookieService cookieService, OtpService otpService) {
        this.service = service;
        this.tokenService = tokenService;
        this.cookieService = cookieService;
        this.otpService = otpService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody LoginRequestDTO dto, HttpServletResponse httpServletResponse){

        AuthenticatedResponseDTO user = service.login(dto);

        return authenticateUser(user, httpServletResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequestDTO dto, HttpServletResponse httpServletResponse) throws MessagingException, IOException {
        service.register(dto);
        otpService.createOtp(dto.email(), "OtpEmailCreateAccount.html");

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        cookieService.clearCookie(response);

        return ResponseEntity.noContent().build();
    }

    @NonNull
    private ResponseEntity<UserResponseDTO> authenticateUser(AuthenticatedResponseDTO user, HttpServletResponse httpServletResponse) {
        String token = this.tokenService.generateToken(user.email());
        cookieService.createCookie(token, httpServletResponse);

        return ResponseEntity.ok(new UserResponseDTO(user.name()));
    }

    @GetMapping("/login")
    public ResponseEntity<Void> deleteOtp(){
        otpService.deleteOtp();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody ForgotPasswordRequestDTO forgotPassword) throws MessagingException, IOException {

        System.out.println("Entrou no forgotPassword");

        service.ensureEmailExists(forgotPassword.email());
        otpService.createOtp(forgotPassword.email(), "OtpEmailForgotPassword.html");

        return ResponseEntity.noContent().build();
    }
}


