package com.novaStack.backend.service;

import com.novaStack.backend.model.OtpCode;
import com.novaStack.backend.model.PendingUser;
import com.novaStack.backend.model.User;
import com.novaStack.backend.repository.OtpRepository;
import com.novaStack.backend.repository.PendingUserRepository;
import com.novaStack.backend.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OtpService {

    private static final SecureRandom random = new SecureRandom();

    private final OtpRepository repository;
    private final EmailService emailService;
    private final PendingUserRepository pendingUserRepository;
    private final AuthService authService;


    public OtpService(OtpRepository repository, EmailService emailService, PendingUserRepository pendingUserRepository, AuthService authService) {
        this.repository = repository;
        this.emailService = emailService;
        this.pendingUserRepository = pendingUserRepository;
        this.authService = authService;
    }

    private String generateOtp() {
        int value = random.nextInt(900000) + 100000;

        return String.valueOf(value);
    }

    @Async
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

    public void verify(String otp, String email) {
        Optional<OtpCode> code = repository.findFirstByEmailOrderByIdDesc(email);
        PendingUser user = this.pendingUserRepository.findByEmail(email);


        if(code.isPresent()){
            if(code.get().getOtpHash().equals(otp)){
                authService.activatePendingUser(user);
                return;
            }

            throw new RuntimeException("codigo incorreto");
        }else{
            throw new RuntimeException("codigo não encontrado no banco de dados");
        }

    }
}
