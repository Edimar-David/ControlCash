package com.novaStack.backend.service;

import com.novaStack.backend.model.OtpCode;
import com.novaStack.backend.repository.OtpRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class OtpService {

    private static final SecureRandom random = new SecureRandom();

    private final OtpRepository repository;

    public OtpService(OtpRepository repository) {
        this.repository = repository;
    }

    private String generateOtp() {
        int value = random.nextInt(900000) + 100000;

        return String.valueOf(value);
    }

    public void createOtp(String email){
        String code = generateOtp();
        OtpCode otp = new OtpCode(
                email,
                code,
                LocalDateTime.now().plusMinutes(1)
        );
        repository.save(otp);
    }

    public void deleteOtp(){
        repository.deleteByTime(LocalDateTime.now());
    }

}
