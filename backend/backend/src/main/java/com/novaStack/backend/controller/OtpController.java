package com.novaStack.backend.controller;

import com.novaStack.backend.dto.auth.AuthenticatedResponseDTO;
import com.novaStack.backend.dto.auth.UserResponseDTO;
import com.novaStack.backend.dto.auth.VerifyOtpDTO;
import com.novaStack.backend.infra.security.TokenService;
import com.novaStack.backend.service.CookieService;
import com.novaStack.backend.service.OtpService;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/otp")
public class OtpController {

    private final TokenService tokenService;
    private final CookieService cookieService;
    private final OtpService otpService;

    public OtpController(TokenService tokenService, CookieService cookieService, OtpService otpService) {
        this.tokenService = tokenService;
        this.cookieService = cookieService;
        this.otpService = otpService;
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpDTO verifyOtp, HttpServletResponse httpServletResponse){
        otpService.verify(verifyOtp.code(), verifyOtp.email());

        return authenticateUser(verifyOtp.email(), httpServletResponse);
    }

    @NonNull
    private ResponseEntity<Void> authenticateUser(String email, HttpServletResponse httpServletResponse) {
        String token = this.tokenService.generateToken(email);
        cookieService.createCookie(token, httpServletResponse);

        return ResponseEntity.noContent().build();
    }
}
