package com.novaStack.backend.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    private String loadTemplate() throws IOException {

        Resource resource =
                new ClassPathResource("templates/email/OtpEmail.html");

        return new String(resource.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8);
    }

    public void sendHtmlEmail(String toEmail, String subject, String otp)
            throws MessagingException, IOException {

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper =
                new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("edimarjr51@gmail.com", "Nova Stack");
        helper.setTo(toEmail);
        helper.setSubject(subject);
        String html = loadTemplate();
        html = html.replace("{{OTP}}", otp);
        helper.setText(html, true);


        mailSender.send(message);
    }
}
