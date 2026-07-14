package com.novaStack.backend.service;

import com.novaStack.backend.model.OtpCode;
import com.novaStack.backend.repository.OtpRepository;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class OtpService {

    private static final SecureRandom random = new SecureRandom();

    private final OtpRepository repository;
    private final EmailService emailService;

    public OtpService(OtpRepository repository, EmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
    }

    private String generateOtp() {
        int value = random.nextInt(900000) + 100000;

        return String.valueOf(value);
    }

    public void createOtp(String email) throws MessagingException, IOException {
        String code = generateOtp();
        emailService.sendHtmlEmail(email, "codigo de verificação", code);
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
