package com.novaStack.backend.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class OtpService {

    private static final SecureRandom random = new SecureRandom();

    public String generateOtp() {
        int value = random.nextInt(900000) + 100000;

        return String.valueOf(value);
    }
}
