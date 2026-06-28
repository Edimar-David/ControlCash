package com.novaStack.backend.controller;


import com.novaStack.backend.dto.auth.AuthenticatedResponseDTO;
import com.novaStack.backend.dto.auth.LoginRequestDTO;
import com.novaStack.backend.dto.auth.RegisterRequestDTO;
import com.novaStack.backend.dto.auth.UserResponseDTO;
import com.novaStack.backend.infra.security.TokenService;
import com.novaStack.backend.service.AuthService;
import com.novaStack.backend.service.CookieService;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;
    private final TokenService tokenService;
    private final CookieService cookieService;

    public AuthController(AuthService service, TokenService tokenService, CookieService cookieService) {
        this.service = service;
        this.tokenService = tokenService;
        this.cookieService = cookieService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody LoginRequestDTO dto, HttpServletResponse httpServletResponse){

        AuthenticatedResponseDTO user = service.login(dto);

        return authenticateUser(user, httpServletResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody RegisterRequestDTO dto, HttpServletResponse httpServletResponse){
        AuthenticatedResponseDTO user = service.register(dto);

        return authenticateUser(user, httpServletResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        cookieService.clearCookie(response);

        return ResponseEntity.noContent().build();
    }

    @NonNull
    private ResponseEntity<UserResponseDTO> authenticateUser(AuthenticatedResponseDTO user, HttpServletResponse httpServletResponse) {
        String token = this.tokenService.generateToken(user);
        cookieService.createCookie(token, httpServletResponse);

        return ResponseEntity.ok(new UserResponseDTO(user.name()));
    }
}


